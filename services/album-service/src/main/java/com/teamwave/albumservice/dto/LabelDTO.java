package com.teamwave.albumservice.dto;

import jakarta.validation.constraints.NotBlank;

public record LabelDTO(
        @NotBlank String name
) {
}
