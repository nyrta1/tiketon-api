package com.wcod.tiketondemo.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(
        name = "event_category",
        indexes = {
                @Index(name = "idx_event_category_name", columnList = "name"),
        }
)
public class EventCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Size(max = 64, message = "Address shouldn't be more than 64 characters")
    private String name;
}
