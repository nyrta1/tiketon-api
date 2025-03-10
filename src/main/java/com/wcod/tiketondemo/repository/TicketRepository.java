package com.wcod.tiketondemo.repository;

import com.wcod.tiketondemo.data.dto.props.TicketResponseDTO;
import com.wcod.tiketondemo.data.models.Ticket;
import com.wcod.tiketondemo.data.models.TicketStatus;
import com.wcod.tiketondemo.data.models.TicketType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    @Query("""
        SELECT new com.wcod.tiketondemo.data.dto.props.TicketResponseDTO(
            t.id, t.row, t.number, t.price, t.ticketType, t.status
        ) 
        FROM Ticket t 
        WHERE t.session.id = :sessionId 
        ORDER BY t.row ASC, t.number ASC
    """)
    List<TicketResponseDTO> findTicketsBySessionId(UUID sessionId);

    Page<Ticket> findAll(Pageable pageable);

    Page<Ticket> findAllByBoughtUserId(UUID userId, Pageable pageable);
}
