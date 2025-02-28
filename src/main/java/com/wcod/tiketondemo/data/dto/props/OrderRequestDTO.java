package com.wcod.tiketondemo.data.dto.props;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestDTO {
    @NotEmpty(message = "Order must contain at least one ticket")
    private List<UUID> ticketIds;
}
