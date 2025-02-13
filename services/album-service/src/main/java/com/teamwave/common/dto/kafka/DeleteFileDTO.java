package com.teamwave.common.dto.kafka;

public record DeleteFileDTO(
        String filename,
        FileType fileType
) {
}
