package com.teamwave.locationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CountryDTO(
        @NotBlank String name,
        @NotBlank String code,
        @NotNull String phoneCode
) {
}
