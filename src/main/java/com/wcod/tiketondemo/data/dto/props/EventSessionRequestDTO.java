package com.wcod.tiketondemo.data.dto.props;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventSessionRequestDTO {
    @NotNull(message = "Event ID is required")
    private UUID eventId;

    @NotNull(message = "Building ID is required")
    private UUID buildingId;

    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;
}
