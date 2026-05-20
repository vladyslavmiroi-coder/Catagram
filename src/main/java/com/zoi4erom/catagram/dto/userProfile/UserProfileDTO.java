package com.zoi4erom.catagram.dto.userProfile;

import com.zoi4erom.catagram.dto.user.UserReadDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserProfileDTO(

        Long id,
        UserReadDTO user,
        String email,
        String bio,
        LocalDate birthDate,
        String country,
        String city,
        LocalDateTime createdAt

) {
}