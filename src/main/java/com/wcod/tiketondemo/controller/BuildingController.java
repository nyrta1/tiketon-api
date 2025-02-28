package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.props.BuildingRequestDTO;
import com.wcod.tiketondemo.data.models.Building;
import com.wcod.tiketondemo.services.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buildings")
@Tag(name = "Building", description = "The Building API. Contains operations for managing buildings.")
public class BuildingController {

    private final BuildingService buildingService;

    @GetMapping("/search/by-city/{cityId}")
    @Operation(summary = "Get buildings by city", description = "Retrieve a list of buildings by city ID")
    public ResponseEntity<Page<Building>> getBuildingsByCity(@PathVariable UUID cityId,
                                                             @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(buildingService.getBuildingsByCity(cityId, pageable));
    }

    @GetMapping("/search/by-address")
    @Operation(summary = "Get building by address", description = "Retrieve a building by its address (case insensitive)")
    public ResponseEntity<Page<Building>> getBuildingByAddress(@RequestParam String address,
                                                               @PageableDefault(size = 10, sort = "address") Pageable pageable) {
        return ResponseEntity.ok(buildingService.getBuildingByAddress(address, pageable));
    }

    @GetMapping("/search/by-name")
    @Operation(summary = "Get buildings by name", description = "Retrieve buildings by name (case insensitive, partial match)")
    public ResponseEntity<Page<Building>> getBuildingsByName(@RequestParam String name,
                                                             @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(buildingService.getBuildingsByName(name, pageable));
    }

    @GetMapping("/search/by-coordinates")
    @Operation(summary = "Get buildings within diagonal range", description = "Retrieve buildings within a 2.0 diagonal range")
    public ResponseEntity<Page<Building>> getBuildingsByCoordinates(@RequestParam Double latitudeX, @RequestParam Double longitudeY,
                                                                    @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(buildingService.getBuildingsWithinCoordinates(latitudeX, longitudeY, pageable));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new building",
            description = "Add a new building to the database (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Building> createBuilding(@Valid @ModelAttribute BuildingRequestDTO requestDTO) {
        return ResponseEntity.ok(buildingService.createBuilding(requestDTO));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update a building",
            description = "Update an existing building (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Building> updateBuilding(@PathVariable UUID id, @Valid @ModelAttribute BuildingRequestDTO requestDTO) {
        return ResponseEntity.ok(buildingService.updateBuilding(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a building",
            description = "Delete a building by ID (Admin only)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteBuilding(@PathVariable UUID id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }
}
