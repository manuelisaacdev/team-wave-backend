package com.teamwave.userservice.dto;

import com.teamwave.userservice.model.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateUserDTO(
        @NotBlank String name,
        @NotNull Gender gender,
        @NotNull LocalDate dateOfBirth,
        @NotNull UUID countryId
    ) {
}
