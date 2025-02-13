package com.teamwave.userservice.service.impl;

import com.teamwave.userservice.config.JWTProperties;
import com.teamwave.userservice.model.User;
import com.teamwave.userservice.security.AuthorizationFilter;
import com.teamwave.userservice.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class JwtServiceImpl implements JwtService {
    private final String name;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final JWTProperties jwtProperties;

    public JwtServiceImpl(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder,
          JWTProperties jwtProperties, @Value("${spring.application.name}") String name) {
        this.name = name;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(User user, long expire, TokenType tokenType) {
        Instant now = Instant.now();
        var claims = JwtClaimsSet.builder()
        .issuedAt(now)
        .issuer(name)
        .expiresAt(now.plusMillis(expire))
        .subject(String.valueOf(user.getId()))
        .claim("authorities", Stream.concat(
                Stream.of(tokenType),
                user.getRoles().stream()
        ).map(GrantedAuthority::getAuthority).toList())
        .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String generateAccessToken(User user) {
        return generateToken(user, jwtProperties.getExpirationAccessToken(), TokenType.ACCESS_TOKEN);
    }

    @Override
    public String generateRefreshToken(User user) {
        return generateToken(user, jwtProperties.getExpirationRefreshToken(), TokenType.REFRESH_TOKEN);
    }

    @Override
    public String generateEmailToken(User user) {
        return generateToken(user, jwtProperties.getExpirationEmailToken(), TokenType.EMAIL_TOKEN);
    }

    @Override
    public UUID getUserId(String authorization) {
        return UUID.fromString(jwtDecoder.decode(authorization.substring(AuthorizationFilter.BEARER.length())).getSubject());
    }

}
