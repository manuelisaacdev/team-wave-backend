package com.teamwave.userservice.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER, ARTIST, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
