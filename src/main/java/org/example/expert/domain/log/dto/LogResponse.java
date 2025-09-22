package org.example.expert.domain.log.dto;

import org.example.expert.domain.log.entity.Log;

import java.time.LocalDateTime;

public record LogResponse(Long requesterId, Long targetManagerId, boolean success, LocalDateTime createdAt) {
    public static LogResponse from(Log log) {
        return new LogResponse(
                log.getRequester().getId(),
                log.getTargetManager().getId(),
                log.isSuccess(),
                log.getCreatedAt()
        );
    }
}
