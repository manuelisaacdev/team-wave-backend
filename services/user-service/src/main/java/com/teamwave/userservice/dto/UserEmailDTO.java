package com.teamwave.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserEmailDTO(
        @NotNull @Email String email
) {
}
