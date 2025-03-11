package com.wcod.tiketondemo.data.dto.props;

import com.wcod.tiketondemo.data.models.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    @Size(min = 2, max = 50)
    private String surname;

    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;

    @NotNull(message = "Role cannot be null")
    private Role role = Role.USER;

    @NotNull(message = "isEnabled cannot be null")
    private Boolean isEnabled = Boolean.TRUE;
}
