package com.wcod.tiketondemo.controller;

import com.wcod.tiketondemo.data.dto.auth.AuthLoginRequest;
import com.wcod.tiketondemo.data.dto.auth.AuthRegisterRequest;
import com.wcod.tiketondemo.data.dto.auth.AuthResponse;
import com.wcod.tiketondemo.data.models.UserEntity;
import com.wcod.tiketondemo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "The Auth API. Contains operations like authentication, registration, assigning roles, deletion of users")
public class AuthController {

    private final UserService authService;

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Register user. [ VALIDATION FOR PASSWORD: LEAST 1 UPPERCASE, 1 LOWERCASE, 1 DIGIT! LENGTH MIN 8 CHARS ]")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody AuthRegisterRequest request
    ){
        return ResponseEntity.ok(authService.registerAndGetAccessToken(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticate user")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthLoginRequest request
    ){
        return ResponseEntity.ok(authService.loginAndGetAccessToken(request));
    }
}

