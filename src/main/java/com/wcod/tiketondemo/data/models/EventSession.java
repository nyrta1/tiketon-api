package com.wcod.tiketondemo.data.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(
        name = "event_session",
        indexes = {
                @Index(name = "idx_session_start_time", columnList = "startTime"),
        }
)
public class EventSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

//    @ManyToOne
//    @JsonBackReference
//    @JoinColumn(name = "building_id", nullable = false)
//    private Building building;

    @Column(nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

//    @JsonIgnore
//    @JsonManagedReference
//    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Seat> seats;
}
