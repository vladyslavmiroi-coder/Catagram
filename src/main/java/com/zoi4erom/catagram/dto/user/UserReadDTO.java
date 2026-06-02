package com.zoi4erom.catagram.dto.user;

import java.util.Set;

public record UserReadDTO(

        Long id,
        String username,
        String phoneNumber,
        String avatarUrl,
        String languageCode,
        Set<String> roles

) {
}