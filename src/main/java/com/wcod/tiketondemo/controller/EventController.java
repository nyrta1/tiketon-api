package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.props.EventRequestDTO;
import com.wcod.tiketondemo.data.models.Event;
import com.wcod.tiketondemo.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Event", description = "Event Management API")
public class EventController {
    private final EventService eventService;

    @GetMapping
    @Operation(summary = "Get all events", description = "Retrieve a list of all available events")
    public ResponseEntity<Page<Event>> getAllEvents(@PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    @GetMapping("/search/by-id/{eventId}")
    @Operation(summary = "Get event by ID", description = "Retrieve a single event by its unique ID")
    public ResponseEntity<Event> getEventById(@PathVariable UUID eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @GetMapping("/search/by-name/{name}")
    @Operation(summary = "Get event by name", description = "Retrieve events by its name")
    public ResponseEntity<Page<Event>> getEventByName(@PathVariable String name,
                                                      @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(eventService.getEventByTitleContainingIgnoreCase(name, pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create a new event",
            description = "Add a new event to the system (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Event> createEvent(@ModelAttribute EventRequestDTO eventRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.createEvent(eventRequestDTO));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update an existing event",
            description = "Modify the details of an existing event (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Event> updateEvent(@PathVariable UUID eventId, @ModelAttribute EventRequestDTO eventRequestDTO) {
        return ResponseEntity.ok(eventService.updateEvent(eventId, eventRequestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{eventId}")
    @Operation(
            summary = "Delete an event",
            description = "Remove an event by its ID (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

}
