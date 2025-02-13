package com.teamwave.albumservice.dto;

import com.teamwave.albumservice.model.AlbumType;
import com.teamwave.albumservice.model.Privacy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

public record AlbumDTO(
    @NotBlank String name,
    @NotBlank String description,
    @NotNull LocalDate releaseDate,
    @NotNull AlbumType albumType,
    @NotNull Privacy privacy
) {
}
