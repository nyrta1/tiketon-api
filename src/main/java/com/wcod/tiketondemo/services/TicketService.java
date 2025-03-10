package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.TicketRequestDTO;
import com.wcod.tiketondemo.data.dto.props.TicketResponseDTO;
import com.wcod.tiketondemo.data.models.*;
import com.wcod.tiketondemo.repository.EventSessionRepository;
import com.wcod.tiketondemo.repository.TicketRepository;
import com.wcod.tiketondemo.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final ModelMapper modelMapper;
    private final TicketRepository ticketRepository;
    private final EventSessionRepository sessionRepository;

    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getTicketsBySession(UUID sessionId) {
        return Optional.of(ticketRepository.findTicketsBySessionId(sessionId))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new CustomException("Tickets by the session id not found!", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Ticket reserveTicket(UserEntity boughtUser, UUID ticketID) {
        Ticket ticket = ticketRepository.findById(ticketID)
                .orElseThrow(() -> new CustomException(
                        String.format("Ticket not found in session for id: %s", ticketID),
                        HttpStatus.NOT_FOUND
                ));

        if (ticket.getStatus() != TicketStatus.AVAILABLE) {
            throw new CustomException("Ticket is already reserved or sold", HttpStatus.BAD_REQUEST);
        }

        ticket.setBoughtUser(boughtUser);
        ticket.setStatus(TicketStatus.SOLD);

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket confirmPayment(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new CustomException("Ticket not found", HttpStatus.NOT_FOUND));

        if (ticket.getStatus() != TicketStatus.PENDING) {
            throw new CustomException("Ticket is already paid or cancelled", HttpStatus.BAD_REQUEST);
        }

        ticket.setStatus(TicketStatus.SOLD);

        return ticketRepository.save(ticket);
    }

    public Page<Ticket> getUserTickets(UUID userId, Pageable pageable) {
        return ticketRepository.findAllByBoughtUserId(userId, pageable);
    }
}
