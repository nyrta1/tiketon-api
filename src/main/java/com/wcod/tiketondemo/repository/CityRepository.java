package com.wcod.tiketondemo.repository;

import com.wcod.tiketondemo.data.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {

}
