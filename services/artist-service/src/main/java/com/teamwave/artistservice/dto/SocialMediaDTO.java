package com.teamwave.artistservice.dto;

import com.teamwave.artistservice.model.SocialMediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SocialMediaDTO(
        @NotBlank String url,
        @NotNull SocialMediaType socialMediaType
) {
}
