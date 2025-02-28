package com.wcod.tiketondemo.data.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginRequest {

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is mandatory")
        @Size(min = 8, max = 64, message = "Password should be between 8 and 64 characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit"
        )
        private String password;
}
