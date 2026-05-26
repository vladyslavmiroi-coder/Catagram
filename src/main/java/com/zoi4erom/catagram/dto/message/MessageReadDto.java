package com.zoi4erom.catagram.dto.message;

import java.time.LocalDateTime;

public record MessageReadDto(
        Long id,

        Long chatId,
        Long senderId,
        String senderUsername,
        String avatarUrl,

        String content,

        Boolean isEdited,
        Boolean isDeleted,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}