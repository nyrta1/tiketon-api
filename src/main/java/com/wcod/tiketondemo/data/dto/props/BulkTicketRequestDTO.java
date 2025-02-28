package com.wcod.tiketondemo.data.dto.props;

import com.wcod.tiketondemo.data.models.TicketType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class BulkTicketRequestDTO {
    @NotNull(message = "Session ID is required")
    private UUID sessionId;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be non-negative")
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Must create at least 1 ticket")
    private Integer quantity;

    @NotNull(message = "Ticket type is required")
    private TicketType ticketType;
}
