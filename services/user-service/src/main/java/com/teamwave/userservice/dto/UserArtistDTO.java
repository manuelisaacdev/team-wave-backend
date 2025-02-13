package com.teamwave.userservice.dto;

import com.teamwave.common.dto.kafka.ArtistDTO;
import jakarta.validation.constraints.NotNull;

public record UserArtistDTO(
        @NotNull UserDTO userDTO,
        @NotNull ArtistDTO artistDTO
) {
}
