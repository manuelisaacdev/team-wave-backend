package com.teamwave.artistservice.dto;

import jakarta.validation.constraints.NotBlank;

public record OccupationDTO(
        @NotBlank String description
) {
}
