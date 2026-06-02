package com.zoi4erom.catagram.dto.chat;

public record ChatUpdateDto(
        Long id,

        String name,
        String description,

        String bannerUrl,

        String visibilityType,
        String languageCode
) {
}