package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.props.OrderRequestDTO;
import com.wcod.tiketondemo.data.models.Order;
import com.wcod.tiketondemo.data.models.UserEntity;
import com.wcod.tiketondemo.services.OrderService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "API for managing orders.")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(
            summary = "Get all orders",
            description = "Retrieve all orders",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Page<Order>> getAllOrders(@PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/by-user")
    @Operation(
            summary = "Get orders by user",
            description = "Retrieve all orders for a specific user",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<Order>> getOrdersByUser(@AuthenticationPrincipal UserDetails userDetails,
                                                       @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        UUID userId = ((UserEntity) userDetails).getId();
        return ResponseEntity.ok(orderService.getOrdersByUser(userId, pageable));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Create an order",
            description = "Place a new order (User only)",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Order> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                             @Valid @ModelAttribute OrderRequestDTO requestDTO) {
        UUID userID = ((UserEntity) userDetails).getId();
        return ResponseEntity.ok(orderService.createOrder(userID, requestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete an order",
            description = "Delete an order by ID (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
