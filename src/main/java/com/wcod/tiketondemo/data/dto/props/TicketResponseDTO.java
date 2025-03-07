package com.wcod.tiketondemo.data.dto.props;

import com.wcod.tiketondemo.data.models.TicketStatus;
import com.wcod.tiketondemo.data.models.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TicketResponseDTO {
    private UUID id;
    private int row;
    private int number;
    private double price;
    private TicketType ticketType;
    private TicketStatus status;
}

