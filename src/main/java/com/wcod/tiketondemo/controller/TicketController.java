package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.props.TicketRequestDTO;
import com.wcod.tiketondemo.data.dto.props.TicketResponseDTO;
import com.wcod.tiketondemo.data.models.Ticket;
import com.wcod.tiketondemo.data.models.UserEntity;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

//    @GetMapping
//    @Operation(summary = "Get all tickets", description = "Retrieve all tickets")
//    public ResponseEntity<Page<Ticket>> getAllTickets(@PageableDefault(size = 10, sort = "price") Pageable pageable) {
//        return ResponseEntity.ok(ticketService.getAllTickets(pageable));
//    }

    @GetMapping("/search/by-session/{sessionId}")
    @Operation(summary = "Get tickets by session", description = "Retrieve tickets for a specific event session")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsBySession(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(ticketService.getTicketsBySession(sessionId));
    }

    @PostMapping("/buy/{ticketID}")
    @Operation(
            summary = "Reserve ticket",
            description = "Reserves a ticket for the authenticated user",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Ticket> reserveTicket(
            @PathVariable UUID ticketID,
            @AuthenticationPrincipal UserEntity currentUser) {
        Ticket reservedTicket = ticketService.reserveTicket(currentUser, ticketID);
        return ResponseEntity.ok(reservedTicket);
    }

}
