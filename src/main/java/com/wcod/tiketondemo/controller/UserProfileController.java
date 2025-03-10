package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.models.Ticket;
import com.wcod.tiketondemo.data.models.UserEntity;
import com.wcod.tiketondemo.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-profile")
@Tag(name = "User Profile", description = "The User Profile API. Contains operations like userinfo, user tickets")
public class UserProfileController {

    private final TicketService ticketService;

    @GetMapping("/userinfo")
    @Operation(
            summary = "Get authenticated user info",
            description = "Get authenticated user info",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<UserEntity> userinfo(@AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/mytickets")
    @Operation(
            summary = "Get authenticated user tickets",
            description = "Get authenticated user tickets",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Page<Ticket>> getUserTickets(
            @AuthenticationPrincipal UserEntity currentUser,
            Pageable pageable
    ) {
        Page<Ticket> tickets = ticketService.getUserTickets(currentUser.getId(), pageable);
        return ResponseEntity.ok(tickets);
    }
}

