package com.teamwave.userservice.service;


import com.teamwave.userservice.dto.AuthenticationResponse;
import com.teamwave.userservice.model.User;

public interface AuthenticationService {
    AuthenticationResponse authenticate(User user);
    AuthenticationResponse refreshToken(String authorization);
}
