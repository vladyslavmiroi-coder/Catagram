package com.zoi4erom.catagram.dto.chat;

public record ChatCreateDto(
        String name,
        String description,
        String avatarUrl,
        String bannerUrl,
        Long ownerId,
        String visibilityType,
        String languageCode
) {
}