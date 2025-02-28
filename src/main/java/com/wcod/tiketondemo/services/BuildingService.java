package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.BuildingRequestDTO;
import com.wcod.tiketondemo.data.models.Building;
import com.wcod.tiketondemo.data.models.City;
import com.wcod.tiketondemo.repository.BuildingRepository;
import com.wcod.tiketondemo.repository.CityRepository;
import com.wcod.tiketondemo.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingService {

    private final ModelMapper modelMapper;
    private final CityRepository cityRepository;
    private final BuildingRepository buildingRepository;

    public Page<Building> getBuildingsByCity(UUID cityId, Pageable pageable) {
        return buildingRepository.findByCityId(cityId, pageable);
    }

    public Page<Building> getBuildingByAddress(String address, Pageable pageable) {
        return buildingRepository.findByAddressIgnoreCaseContaining(address, pageable);
    }

    public Page<Building> getBuildingsByName(String name, Pageable pageable) {
        return buildingRepository.findByNameIgnoreCaseContaining(name, pageable);
    }

    public Page<Building> getBuildingsWithinCoordinates(Double latitude, Double longitude, Pageable pageable) {
        return buildingRepository.findByCoordinatesWithinDiagonal(latitude, longitude, pageable);
    }

    public Building createBuilding(BuildingRequestDTO requestDTO) {
        Building building = modelMapper.map(requestDTO, Building.class);
        City locatedCity = cityRepository.findById(requestDTO.getCityID())
                .orElseThrow(() -> new CustomException(String.format("Not found city by ID: %s", requestDTO.getCityID()), HttpStatus.NOT_FOUND));
        building.setCity(locatedCity);
        return buildingRepository.save(building);
    }

    public Building updateBuilding(UUID id, BuildingRequestDTO requestDTO) {
        Building existingBuilding = buildingRepository.findById(id)
                .orElseThrow(() -> new CustomException(String.format("Building not found: %s", requestDTO.toString()), HttpStatus.NOT_FOUND));
        modelMapper.map(requestDTO, existingBuilding);
        return buildingRepository.saveAndFlush(existingBuilding);
    }

    public void deleteBuilding(UUID id) {
        buildingRepository.deleteById(id);
    }
}
