package com.wcod.tiketondemo.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(
        name = "city",
        indexes = {
                @Index(name = "idx_city_name", columnList = "name")
        }
)
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 64, message = "City name should not be more than 64 characters and less than 2 characters")
    private String name;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Building> buildings;
}
