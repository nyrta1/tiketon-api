package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.props.CityRequestDTO;
import com.wcod.tiketondemo.data.models.City;
import com.wcod.tiketondemo.services.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
@Tag(name = "City", description = "The City API. Contains operations for managing cities.")
public class CityController {

    private final CityService cityService;

    @GetMapping
    @Operation(summary = "Get all cities", description = "Retrieve a list of all cities")
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create a new city",
            description = "Add a new city to the database",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<City> createCity(@Valid @ModelAttribute CityRequestDTO cityRequestDTO) {
        return ResponseEntity.ok(cityService.saveCity(cityRequestDTO));
    }
}
