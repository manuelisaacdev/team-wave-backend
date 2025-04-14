package com.teamwave.userservice.service;


import com.teamwave.userservice.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

public interface JwtService {
    UUID getUserId(String authorization);
    String generateEmailToken(User user);
    String generateAccessToken(User user);
    String generateRefreshToken(User user);

    enum TokenType implements GrantedAuthority {
        ACCESS_TOKEN, REFRESH_TOKEN, EMAIL_TOKEN;

        public String getAuthority() {
            return name();
        }
    }
}
