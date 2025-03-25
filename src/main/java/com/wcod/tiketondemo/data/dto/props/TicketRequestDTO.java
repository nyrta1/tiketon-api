package com.wcod.tiketondemo.data.dto.props;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class TicketRequestDTO {
    @NotNull(message = "Session ID is required")
    private UUID sessionId;

    @NotNull(message = "Seat row is required")
    private Integer row;

    @NotNull(message = "Seat number is required")
    private Integer seatNumber;
}
