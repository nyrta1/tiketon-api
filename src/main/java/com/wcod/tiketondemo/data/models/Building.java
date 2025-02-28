package com.wcod.tiketondemo.data.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(
        name = "building",
        indexes = {
                @Index(name = "idx_building_name", columnList = "name"),
                @Index(name = "idx_building_address", columnList = "address"),
                @Index(name = "idx_building_coordinates", columnList = "latitudeX, longitudeY")
        }
)
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Size(max = 128, message = "Building name should be no more than 128 characters")
    private String name;

    @Column(nullable = false)
    @Size(min = 5, max = 128, message = "Address should be between 5 and 128 characters")
    private String address;

    @Column(nullable = false)
    @Min(-90) @Max(90)
    private Double latitudeX;

    @Column(nullable = false)
    @Min(-180) @Max(180)
    private Double longitudeY;

    @Column(nullable = false)
    private Boolean hasParking;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}
