package com.teamwave.artistservice.dto;

import com.teamwave.artistservice.model.PhoneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdatePhoneDTO(
        @NotBlank UUID phoneId,
        @NotBlank String number,
        @NotBlank PhoneType phoneType,
        @NotNull UUID countryId) {
}
