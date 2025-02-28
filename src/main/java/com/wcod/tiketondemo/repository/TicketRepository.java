package com.wcod.tiketondemo.repository;

import com.wcod.tiketondemo.data.models.Ticket;
import com.wcod.tiketondemo.data.models.TicketStatus;
import com.wcod.tiketondemo.data.models.TicketType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findBySessionId(UUID sessionId);
    List<Ticket> findByTicketType(TicketType ticketType);
    List<Ticket> findByStatus(TicketStatus status);

    Page<Ticket> findBySessionId(UUID sessionId, Pageable pageable);
    Page<Ticket> findByTicketType(TicketType ticketType, Pageable pageable);
    Page<Ticket> findByStatus(TicketStatus status, Pageable pageable);
    Page<Ticket> findAll(Pageable pageable);
}
