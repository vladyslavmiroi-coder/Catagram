package com.zoi4erom.catagram.dto.message;

import java.time.LocalDateTime;

public record MessageReadDto(
        Long id,
        Long chatId,
        Long senderId,
        String senderUsername,
        String senderLanguageCode,
        String avatarUrl,
        String content,
        Boolean isEdited,
        Boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
        public MessageReadDto withContent(String newContent) {
                return new MessageReadDto(
                        this.id,
                        this.chatId,
                        this.senderId,
                        this.senderUsername,
                        this.senderLanguageCode,
                        this.avatarUrl,
                        newContent,
                        this.isEdited,
                        this.isDeleted,
                        this.createdAt,
                        this.updatedAt
                );
        }
}