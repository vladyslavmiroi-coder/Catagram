package com.zoi4erom.catagram.dto.chat;

import java.time.LocalDateTime;

public record ChatDTO(
        Long id,
        String name,
        String description,
        String avatarUrl,
        String bannerUrl,
        String visibilityType,
        Long ownerId,
        String ownerUsername,
        String languageCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        int membersCount,
        int messagesCount
) {
}