package com.wcod.tiketondemo.repository;

import com.wcod.tiketondemo.data.models.EventSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventSessionRepository extends JpaRepository<EventSession, UUID> {
    List<EventSession> findByEventId(UUID eventId);
    List<EventSession> findByBuildingId(UUID buildingId);
    List<EventSession> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    Page<EventSession> findByEventId(UUID eventId, Pageable pageable);
    Page<EventSession> findByBuildingId(UUID buildingId, Pageable pageable);
    Page<EventSession> findByStartTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<EventSession> findAll(Pageable pageable);
}
