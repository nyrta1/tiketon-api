package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.props.UserUpdateRequestDTO;
import com.wcod.tiketondemo.data.models.UserEntity;
import com.wcod.tiketondemo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "ADMIN", description = "The ADMIN API. Contains operations like reset password, update user info.")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(
            summary = "Get all users [REQUIRES ADMIN PRIVILEGE]",
            description = "Returns paginated list of all users",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Page<UserEntity>> getAllUsersByPagination(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsersByPagination(pageable));
    }


    @PutMapping("/users/password-reset/{userID}")
    @Operation(
            summary = "Reset user password [REQUIRES ADMIN PRIVILEGE]",
            description = "Reset user password",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<UserEntity> passwordReset(@PathVariable UUID userID,
                                                    @RequestParam(name = "password") String password) {
        return ResponseEntity.ok(userService.resetPasswordByUserID(userID, password));
    }

    @PutMapping(path = "/users/{userID}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update user information [REQUIRES ADMIN PRIVILEGE]",
            description = "Updates user details (name, surname, phone number, role, account status)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<UserEntity> updateUserInfo(@PathVariable UUID userID,
                                                     @ModelAttribute UserUpdateRequestDTO updateRequestDTO) {
        return ResponseEntity.ok(userService.updateUserInfo(userID, updateRequestDTO));
    }
}

