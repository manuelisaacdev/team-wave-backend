package com.teamwave.userservice.service.impl;

import com.teamwave.userservice.dto.AuthenticationResponse;
import com.teamwave.userservice.model.User;
import com.teamwave.userservice.security.AuthorizationFilter;
import com.teamwave.userservice.service.AuthenticationService;
import com.teamwave.userservice.service.JwtService;
import com.teamwave.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public AuthenticationResponse authenticate(User user) {
        userService.hasLoggedIn(user);
        return new AuthenticationResponse(user, jwtService.generateAccessToken(user), jwtService.generateRefreshToken(user));
    }

    @Override
    public AuthenticationResponse refreshToken(String authorization) {
        User user = userService.findById(jwtService.getUserId(authorization.substring(AuthorizationFilter.BEARER.length())));
        return new AuthenticationResponse(user, jwtService.generateAccessToken(user), jwtService.generateRefreshToken(user));
    }

}
