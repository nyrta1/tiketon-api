package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.EventRequestDTO;
import com.wcod.tiketondemo.data.models.Event;
import com.wcod.tiketondemo.data.models.EventCategory;
import com.wcod.tiketondemo.repository.EventCategoryRepository;
import com.wcod.tiketondemo.repository.EventRepository;
import com.wcod.tiketondemo.shared.exception.CustomException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    @Value("${minio.url}")
    private String minioEndpointUrl;

    private final ModelMapper modelMapper;
    private final MinioClient minioClient;
    private final EventRepository eventRepository;
    private final EventCategoryRepository categoryRepository;

    public Page<Event> getAllEvents(Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        events.forEach(this::attachPublicImageUrl);
        return events;
    }

    public Event getEventById(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(
                        String.format("Event not found by ID: %s", eventId),
                        HttpStatus.NOT_FOUND
                ));
        attachPublicImageUrl(event);
        return event;
    }

    public Page<Event> getEventByTitleContainingIgnoreCase(String name, Pageable pageable) {
        Page<Event> events = eventRepository.findByTitleContainingIgnoreCase(name, pageable);
        events.forEach(this::attachPublicImageUrl);
        return events;
    }

    @Transactional
    public Event createEvent(EventRequestDTO requestDTO) {
        Event event = modelMapper.map(requestDTO, Event.class);
        EventCategory category = categoryRepository.findById(requestDTO.getCategoryID())
                .orElseThrow(() -> new CustomException(
                        String.format("Category not found by ID: %s", requestDTO.getCategoryID()), HttpStatus.NOT_FOUND));
        event.setCategory(category);

        Event savedEvent = eventRepository.save(event);

        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket("public-bucket")
                    .object(savedEvent.getId().toString())
                    .stream(requestDTO.getBackgroundImage().getInputStream(),
                            requestDTO.getBackgroundImage().getSize(), -1)
                    .contentType(requestDTO.getBackgroundImage().getContentType())
                    .build();
            minioClient.putObject(args);
            this.attachPublicImageUrl(savedEvent);
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException("Failed to upload image to MinIO", e);
        }

        return savedEvent;
    }

    @Transactional
    public Event updateEvent(UUID eventId, EventRequestDTO requestDTO) {
        Event existingEvent = getEventById(eventId);

        modelMapper.map(requestDTO, existingEvent);

        if (requestDTO.getBackgroundImage() != null) {
            try {
                RemoveObjectArgs removeArgs = RemoveObjectArgs.builder()
                        .bucket("public-bucket")
                        .object(existingEvent.getId().toString())
                        .build();
                minioClient.removeObject(removeArgs);
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete old image from MinIO", e);
            }

            try {
                PutObjectArgs putArgs = PutObjectArgs.builder()
                        .bucket("public-bucket")
                        .object(existingEvent.getId().toString())
                        .stream(requestDTO.getBackgroundImage().getInputStream(),
                                requestDTO.getBackgroundImage().getSize(), -1)
                        .contentType(requestDTO.getBackgroundImage().getContentType())
                        .build();
                minioClient.putObject(putArgs);
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload new image to MinIO", e);
            }
        }

        return eventRepository.save(existingEvent);
    }


    public void deleteEvent(UUID eventId) {
        throw new CustomException(
                "This service method is temporarily not working...",
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    private void attachPublicImageUrl(Event event) {
        String bucketName = "public-bucket";
        event.setImageUrl(String.format("%s/%s/%s", minioEndpointUrl, bucketName, event.getId().toString()));
    }
}
