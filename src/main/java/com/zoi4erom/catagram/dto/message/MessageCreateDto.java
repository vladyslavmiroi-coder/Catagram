package com.zoi4erom.catagram.dto.message;

public record MessageCreateDto(
        Long chatId,
        Long senderId,
        String content
) {
}