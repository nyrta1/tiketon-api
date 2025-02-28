package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.props.BulkDeleteRequestDTO;
import com.wcod.tiketondemo.data.dto.props.BulkTicketRequestDTO;
import com.wcod.tiketondemo.data.dto.props.TicketRequestDTO;
import com.wcod.tiketondemo.data.models.Ticket;
import com.wcod.tiketondemo.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
@Tag(name = "Ticket", description = "API for managing tickets.")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    @Operation(summary = "Get all tickets", description = "Retrieve all tickets")
    public ResponseEntity<Page<Ticket>> getAllTickets(@PageableDefault(size = 10, sort = "price") Pageable pageable) {
        return ResponseEntity.ok(ticketService.getAllTickets(pageable));
    }

    @GetMapping("/search/by-session/{sessionId}")
    @Operation(summary = "Get tickets by session", description = "Retrieve tickets for a specific event session")
    public ResponseEntity<Page<Ticket>> getTicketsBySession(@PathVariable UUID sessionId,
                                                            @PageableDefault(size = 10, sort = "price") Pageable pageable) {
        return ResponseEntity.ok(ticketService.getTicketsBySession(sessionId, pageable));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new ticket",
            description = "Add a new ticket (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Ticket> createTicket(@Valid @ModelAttribute TicketRequestDTO requestDTO) {
        return ResponseEntity.ok(ticketService.createTicket(requestDTO));
    }

    @PostMapping(path = "/bulk-create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Bulk create tickets",
            description = "Create multiple tickets at once (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<List<Ticket>> createBulkTickets(@Valid @ModelAttribute BulkTicketRequestDTO requestDTO) {
        return ResponseEntity.ok(ticketService.createBulkTickets(requestDTO));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update a ticket",
            description = "Update an existing ticket (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Ticket> updateTicket(@PathVariable UUID id, @Valid @ModelAttribute TicketRequestDTO requestDTO) {
        return ResponseEntity.ok(ticketService.updateTicket(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a ticket",
            description = "Delete a ticket by ID (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/bulk-delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Bulk delete tickets",
            description = "Delete multiple tickets by ID (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteBulkTickets(@Valid @ModelAttribute BulkDeleteRequestDTO requestDTO) {
        ticketService.deleteBulkTickets(requestDTO);
        return ResponseEntity.noContent().build();
    }

}
