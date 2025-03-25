package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.EventSessionPutRequestDTO;
import com.wcod.tiketondemo.data.dto.props.EventSessionRequestDTO;
import com.wcod.tiketondemo.data.models.*;
import com.wcod.tiketondemo.repository.EventRepository;
import com.wcod.tiketondemo.repository.EventSessionRepository;
import com.wcod.tiketondemo.repository.TicketRepository;
import com.wcod.tiketondemo.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventSessionService {
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final EventSessionRepository eventSessionRepository;

    public Page<EventSession> getAllSessions(Pageable pageable) {
        return eventSessionRepository.findAll(pageable);
    }

    public Page<EventSession> getSessionsByEvent(UUID eventId, Pageable pageable) {
        return eventSessionRepository.findByEventId(eventId, pageable);
    }

    @Transactional
    public EventSession createSession(EventSessionRequestDTO requestDTO) {
        Event event = eventRepository.findById(requestDTO.getEventId())
                .orElseThrow(() -> new CustomException(
                        String.format("Event not found by ID: %s", requestDTO.getEventId()), HttpStatus.NOT_FOUND));

        EventSession session = new EventSession();
        session.setEvent(event);
        session.setStartTime(requestDTO.getStartTime().toLocalDateTime());

        EventSession savedSession = eventSessionRepository.save(session);

        List<Ticket> tickets = new ArrayList<>();
        Ticket.SEAT_MAP.forEach((row, seatNumbers) -> {
            for (int number : seatNumbers) {
                Ticket ticket = new Ticket();
                ticket.setRow(row);
                ticket.setNumber(number);
                ticket.setSession(savedSession);
                ticket.setPrice(requestDTO.getPrice());
                tickets.add(ticket);
            }
        });

        ticketRepository.saveAll(tickets);

        return savedSession;
    }

    @Transactional
    public EventSession updateSession(UUID id, EventSessionPutRequestDTO requestDTO) {
        EventSession session = eventSessionRepository.findById(id)
                .orElseThrow(() -> new CustomException(String.format("Event Session not found by ID: %s", id), HttpStatus.NOT_FOUND));

        session.setStartTime(requestDTO.getStartTime().toLocalDateTime());

        return eventSessionRepository.saveAndFlush(session);
    }

    public void deleteSession(UUID id) {
        eventSessionRepository.deleteById(id);
    }
}
