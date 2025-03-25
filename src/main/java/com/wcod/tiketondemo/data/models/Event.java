package com.wcod.tiketondemo.data.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(
        name = "event",
        indexes = {
                @Index(name = "idx_event_name", columnList = "title"),
        }
)

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 128)
    @Size(min = 2, max = 128, message = "Title should be between 2 and 128 characters")
    private String title;

    @Column(length = 2500)
    @Size(max = 2500, message = "Title should be no more 2500 characters")
    private String description;

    @Column(length = 2500)
    @Size(max = 2500, message = "Title should be no more 2500 characters")
    private String additionalInformation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private EventCategory category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @JsonManagedReference
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventSession> sessions;

    @Transient
    private String imageUrl;
}
