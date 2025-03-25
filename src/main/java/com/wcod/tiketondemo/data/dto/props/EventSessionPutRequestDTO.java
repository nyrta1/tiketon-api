package com.wcod.tiketondemo.data.dto.props;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class EventSessionPutRequestDTO {
    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private OffsetDateTime startTime;
}
