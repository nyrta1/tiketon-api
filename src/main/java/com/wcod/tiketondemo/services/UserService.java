package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.auth.AuthLoginRequest;
import com.wcod.tiketondemo.data.dto.auth.AuthRegisterRequest;
import com.wcod.tiketondemo.data.dto.auth.AuthResponse;
import com.wcod.tiketondemo.data.models.Role;
import com.wcod.tiketondemo.data.models.UserEntity;
import com.wcod.tiketondemo.repository.UserRepository;
import com.wcod.tiketondemo.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registerAndGetAccessToken(AuthRegisterRequest request) {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new IllegalStateException("Phone number is taken: " + request.getPhoneNumber());
        }

        UserEntity user = modelMapper.map(request, UserEntity.class);
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setRole(Role.USER);

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        String expireTime = JwtService.getExpireTime();

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .expiresIn(expireTime)
                .user(user)
                .build();
    }


    public AuthResponse loginAndGetAccessToken(AuthLoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhoneNumber(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid phone number or password", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() ->  new CustomException(String.format("User not found by phone number: %s", request.getPhoneNumber()), HttpStatus.NOT_FOUND));

        String jwtToken = jwtService.generateToken(user);
        String expireTime = JwtService.getExpireTime();

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .expiresIn(expireTime)
                .user(user)
                .build();
    }
}

