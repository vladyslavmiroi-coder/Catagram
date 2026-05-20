package com.zoi4erom.catagram.dto.user;

public record UserReadDTO(

        Long id,
        String username,
        String phoneNumber,
        String avatarUrl,
        String languageCode

) {
}