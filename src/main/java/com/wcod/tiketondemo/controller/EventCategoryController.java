package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.props.EventCategoryRequestDTO;
import com.wcod.tiketondemo.data.models.EventCategory;
import com.wcod.tiketondemo.services.EventCategoryService;
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
@RequestMapping("/api/event-categories")
@Tag(name = "Event Category", description = "The Event Category API. Contains operations for managing event categories.")
public class EventCategoryController {

    private final EventCategoryService eventCategoryService;

    @GetMapping
    @Operation(summary = "Get all event categories", description = "Retrieve a list of all event categories")
    public ResponseEntity<List<EventCategory>> getAllCategories() {
        return ResponseEntity.ok(eventCategoryService.getAllCategories());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create a new event category",
            description = "Add a new event category to the database",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<EventCategory> createCategory(@Valid @ModelAttribute EventCategoryRequestDTO requestDTO) {
        return ResponseEntity.ok(eventCategoryService.createCategory(requestDTO));
    }
}
