package com.wcod.tiketondemo.repository;

import com.wcod.tiketondemo.data.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {

    List<Building> findByCityId(UUID cityId);

    List<Building> findByAddressIgnoreCaseContaining(String address);

    List<Building> findByNameIgnoreCaseContaining(String name);

    @Query("SELECT b FROM Building b WHERE SQRT(POWER(b.latitudeX - :latitude, 2) + POWER(b.longitudeY - :longitude, 2)) <= 2.0")
    List<Building> findByCoordinatesWithinDiagonal(Double latitude, Double longitude);

    Page<Building> findAll(Pageable pageable);
    Page<Building> findByCityId(UUID cityId, Pageable pageable);

    Page<Building> findByAddressIgnoreCaseContaining(String address, Pageable pageable);

    Page<Building> findByNameIgnoreCaseContaining(String name, Pageable pageable);

    @Query("SELECT b FROM Building b WHERE SQRT(POWER(b.latitudeX - :latitude, 2) + POWER(b.longitudeY - :longitude, 2)) <= 2.0")
    Page<Building> findByCoordinatesWithinDiagonal(Double latitude, Double longitude, Pageable pageable);
}
