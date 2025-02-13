package com.teamwave.fileservice.dto;

import com.teamwave.common.dto.kafka.FileType;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UploadDTO(
        @NotNull MultipartFile file,
        @NotNull FileType fileType,
        @NotNull UUID ownerId
    ) {
}
