package com.teamwave.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record RecoveryDTO(
        @NotBlank String email,
        @NotBlank String password
) {
}
