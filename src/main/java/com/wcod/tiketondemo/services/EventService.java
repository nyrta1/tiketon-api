package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.EventRequestDTO;
import com.wcod.tiketondemo.data.models.Event;
import com.wcod.tiketondemo.data.models.EventCategory;
import com.wcod.tiketondemo.repository.EventCategoryRepository;
import com.wcod.tiketondemo.repository.EventRepository;
import com.wcod.tiketondemo.shared.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private final EventCategoryRepository categoryRepository;

    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Event getEventById(UUID eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(
                        String.format("Event not found by ID: %s", eventId),
                        HttpStatus.NOT_FOUND
                ));
    }

    public Page<Event> getEventByTitleContainingIgnoreCase(String name, Pageable pageable) {
        return eventRepository.findByTitleContainingIgnoreCase(name, pageable);
    }

    @Transactional
    public Event createEvent(EventRequestDTO requestDTO) {
        Event event = modelMapper.map(requestDTO, Event.class);
        EventCategory category = categoryRepository.findById(requestDTO.getCategoryID())
                .orElseThrow(() -> new CustomException(String.format("Category not found by ID: %s", requestDTO.getCategoryID()), HttpStatus.NOT_FOUND));
        event.setCategory(category);
        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(UUID eventId, EventRequestDTO requestDTO) {
        Event existingEvent = getEventById(eventId);
        modelMapper.map(requestDTO, existingEvent);
        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(UUID eventId) {
        throw new CustomException(
                "This service method is temporarily not working...",
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }
}
