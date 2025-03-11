package com.wcod.tiketondemo.data.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum Role {
    USER,
    ADMIN;

    public Set<GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}
