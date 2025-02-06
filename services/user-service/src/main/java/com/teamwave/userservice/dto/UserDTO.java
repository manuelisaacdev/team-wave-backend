package com.teamwave.userservice.dto;

import com.teamwave.userservice.model.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record UserDTO(
        @NotBlank String name,
        @NotNull Gender gender,
        @NotNull LocalDate dateOfBirth,
        @NotBlank String email,
        @NotNull UUID countryId,
        @NotBlank @Size(min = 8, max = 8) String password
        ) {
}
