package com.teamwave.common.dto.kafka;

public record EmailDTO(
        String to,
        String subject,
        String body
) {
}
