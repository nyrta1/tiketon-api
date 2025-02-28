package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.EventSessionRequestDTO;
import com.wcod.tiketondemo.data.models.Building;
import com.wcod.tiketondemo.data.models.Event;
import com.wcod.tiketondemo.data.models.EventSession;
import com.wcod.tiketondemo.repository.BuildingRepository;
import com.wcod.tiketondemo.repository.EventRepository;
import com.wcod.tiketondemo.repository.EventSessionRepository;
import com.wcod.tiketondemo.shared.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventSessionService {
    private final EventSessionRepository eventSessionRepository;
    private final EventRepository eventRepository;
    private final BuildingRepository buildingRepository;

    public Page<EventSession> getAllSessions(Pageable pageable) {
        return eventSessionRepository.findAll(pageable);
    }

    public Page<EventSession> getSessionsByEvent(UUID eventId, Pageable pageable) {
        return eventSessionRepository.findByEventId(eventId, pageable);
    }

    @Transactional
    public EventSession createSession(EventSessionRequestDTO requestDTO) {
        Event event = eventRepository.findById(requestDTO.getEventId())
                .orElseThrow(() -> new CustomException(String.format("Event not found by ID: %s", requestDTO.getEventId()), HttpStatus.NOT_FOUND));
        Building building = buildingRepository.findById(requestDTO.getBuildingId())
                .orElseThrow(() -> new CustomException(String.format("Building not found by ID: %s", requestDTO.getBuildingId()), HttpStatus.NOT_FOUND));

        EventSession session = new EventSession();
        session.setEvent(event);
        session.setBuilding(building);
        session.setStartTime(requestDTO.getStartTime());

        return eventSessionRepository.saveAndFlush(session);
    }

    @Transactional
    public EventSession updateSession(UUID id, EventSessionRequestDTO requestDTO) {
        EventSession session = eventSessionRepository.findById(id)
                .orElseThrow(() -> new CustomException(String.format("Event Session not found by ID: %s", id), HttpStatus.NOT_FOUND));

        Event event = eventRepository.findById(requestDTO.getEventId())
                .orElseThrow(() -> new CustomException(String.format("Event not found by ID: %s", requestDTO.getEventId()), HttpStatus.NOT_FOUND));
        Building building = buildingRepository.findById(requestDTO.getBuildingId())
                .orElseThrow(() -> new CustomException(String.format("Building not found by ID: %s", requestDTO.getBuildingId()), HttpStatus.NOT_FOUND));

        session.setEvent(event);
        session.setBuilding(building);
        session.setStartTime(requestDTO.getStartTime());

        return eventSessionRepository.saveAndFlush(session);
    }

    public void deleteSession(UUID id) {
        eventSessionRepository.deleteById(id);
    }
}
