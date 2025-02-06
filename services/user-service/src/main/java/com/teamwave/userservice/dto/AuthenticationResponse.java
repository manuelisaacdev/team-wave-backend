package com.teamwave.userservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.teamwave.userservice.model.User;

@JsonPropertyOrder({"accessToken", "refreshToken", "user"})
public record AuthenticationResponse(User user, String accessToken, String refreshToken) {
}
