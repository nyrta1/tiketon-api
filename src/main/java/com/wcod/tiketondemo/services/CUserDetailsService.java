package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.config.StringCryptoConverter;
import com.wcod.tiketondemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final StringCryptoConverter stringCryptoConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User not found by email: %s", username)
                ));
    }
}

