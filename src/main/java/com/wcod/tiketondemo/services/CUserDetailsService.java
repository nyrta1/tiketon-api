package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.config.StringCryptoConverter;
import com.wcod.tiketondemo.repository.UserRepository;
import com.wcod.tiketondemo.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new CustomException(
                        String.format("User not found by phone number: %s", username),
                        HttpStatus.NOT_FOUND
                ));
    }
}

