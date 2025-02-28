package com.wcod.tiketondemo.data.dto.props;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequestDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "LatitudeX is required")
    private Double latitudeX;

    @NotNull(message = "LongitudeY is required")
    private Double longitudeY;

    @NotNull(message = "HasParking is required")
    private Boolean hasParking;

    @NotNull(message = "City ID is required")
    private UUID cityID;
}
