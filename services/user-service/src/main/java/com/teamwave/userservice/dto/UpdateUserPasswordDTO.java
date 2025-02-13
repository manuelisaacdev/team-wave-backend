package com.teamwave.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordDTO(
        @NotBlank String currentPassword,
        @NotBlank @Size(min = 8, max = 8) String newPassword
) {
}
