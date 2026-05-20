package com.zoi4erom.catagram.dto.user;

public record UserUpdateDTO(

        Long id,
        String username,
        String phoneNumber,
        String languageCode

) {
}