package com.wcod.tiketondemo.repository;

import com.wcod.tiketondemo.data.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByTitleContainingIgnoreCase(String title);

    Page<Event> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Event> findAll(Pageable pageable);
}
