package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.BulkDeleteRequestDTO;
import com.wcod.tiketondemo.data.dto.props.BulkTicketRequestDTO;
import com.wcod.tiketondemo.data.dto.props.TicketRequestDTO;
import com.wcod.tiketondemo.data.models.EventSession;
import com.wcod.tiketondemo.data.models.Ticket;
import com.wcod.tiketondemo.repository.EventSessionRepository;
import com.wcod.tiketondemo.repository.TicketRepository;
import com.wcod.tiketondemo.shared.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final ModelMapper modelMapper;
    private final TicketRepository ticketRepository;
    private final EventSessionRepository sessionRepository;

    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Page<Ticket> getTicketsBySession(UUID sessionId, Pageable pageable) {
        return ticketRepository.findBySessionId(sessionId, pageable);
    }

    @Transactional
    public Ticket createTicket(TicketRequestDTO requestDTO) {
        Ticket ticket = modelMapper.map(requestDTO, Ticket.class);
        EventSession session = sessionRepository.findById(requestDTO.getSessionId())
                .orElseThrow(() -> new CustomException(String.format("Session not found by ID: %s", requestDTO.getSessionId()), HttpStatus.NOT_FOUND));
        ticket.setSession(session);
        return ticketRepository.saveAndFlush(ticket);
    }

    @Transactional
    public List<Ticket> createBulkTickets(BulkTicketRequestDTO requestDTO) {
        EventSession session = sessionRepository.findById(requestDTO.getSessionId())
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        List<Ticket> tickets = IntStream.range(0, requestDTO.getQuantity())
                .mapToObj(i -> {
                    Ticket ticket = new Ticket();
                    ticket.setSession(session);
                    ticket.setPrice(requestDTO.getPrice());
                    ticket.setTicketType(requestDTO.getTicketType());
                    return ticket;
                })
                .toList();

        return ticketRepository.saveAll(tickets);
    }

    @Transactional
    public Ticket updateTicket(UUID id, TicketRequestDTO requestDTO) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        ticket.setPrice(requestDTO.getPrice());
        ticket.setTicketType(requestDTO.getTicketType());

        return ticketRepository.saveAndFlush(ticket);
    }

    public void deleteTicket(UUID id) {
        ticketRepository.deleteById(id);
    }

    @Transactional
    public void deleteBulkTickets(BulkDeleteRequestDTO requestDTO) {
        ticketRepository.deleteAllById(requestDTO.getTicketIds());
    }

}
