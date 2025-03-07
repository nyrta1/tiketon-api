package com.wcod.tiketondemo.data.dto.props;

import com.wcod.tiketondemo.data.models.AgeRestriction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {
    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "description is mandatory")
    private String description;

    @NotBlank(message = "additionalInformation is mandatory")
    private String additionalInformation;

    @NotNull(message = "categoryID is required")
    private UUID categoryID;

    @NotNull(message = "ageRestriction is required")
    private AgeRestriction ageRestriction;

    private MultipartFile backgroundImage;
}
