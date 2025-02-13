package com.teamwave.userservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("jwt")
public class JWTProperties {
    private Long expirationEmailToken;
    private Long expirationAccessToken;
    private Long expirationRefreshToken;
}
