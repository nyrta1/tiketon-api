package com.wcod.tiketondemo.data.dto.props;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityRequestDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;
}
