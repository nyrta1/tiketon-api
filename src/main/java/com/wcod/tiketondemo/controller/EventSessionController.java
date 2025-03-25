package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.props.EventSessionPutRequestDTO;
import com.wcod.tiketondemo.data.dto.props.EventSessionRequestDTO;
import com.wcod.tiketondemo.data.dto.props.TicketResponseDTO;
import com.wcod.tiketondemo.data.models.EventSession;
import com.wcod.tiketondemo.services.EventSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-sessions")
@Tag(name = "Event Session", description = "API for managing event sessions.")
public class EventSessionController {

    private final EventSessionService eventSessionService;

//    @GetMapping
//    @Operation(
//            summary = "Get all event sessions",
//            description = "Retrieve all event sessions"
//    )
//    public ResponseEntity<Page<EventSession>> getAllSessions(@PageableDefault(size = 10, sort = "startTime") Pageable pageable) {
//        return ResponseEntity.ok(eventSessionService.getAllSessions(pageable));
//    }

    @GetMapping("/search/by-event/{eventId}")
    @Operation(
            summary = "Get sessions by event",
            description = "Retrieve event sessions for a specific event"
    )
    public ResponseEntity<Page<EventSession>> getSessionsByEvent(@PathVariable UUID eventId,
                                                                 @PageableDefault(size = 10, sort = "startTime") Pageable pageable) {
        return ResponseEntity.ok(eventSessionService.getSessionsByEvent(eventId, pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create a new event session",
            description = "Add a new event session (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<EventSession> createSession(@Valid @ModelAttribute EventSessionRequestDTO requestDTO) {
        return ResponseEntity.ok(eventSessionService.createSession(requestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update an event session",
            description = "Update an existing event session (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<EventSession> updateSession(@PathVariable UUID id, @Valid @ModelAttribute EventSessionPutRequestDTO requestDTO) {
        return ResponseEntity.ok(eventSessionService.updateSession(id, requestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an event session",
            description = "Delete an event session by ID (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteSession(@PathVariable UUID id) {
        eventSessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
