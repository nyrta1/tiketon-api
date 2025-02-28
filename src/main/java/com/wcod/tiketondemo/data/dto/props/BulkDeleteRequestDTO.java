package com.wcod.tiketondemo.data.dto.props;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BulkDeleteRequestDTO {
    @NotEmpty(message = "Ticket ID list cannot be empty")
    private List<UUID> ticketIds;
}
