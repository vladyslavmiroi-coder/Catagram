package com.zoi4erom.catagram.dto.friend;

import java.time.LocalDateTime;

public record FriendshipReadDTO(
        Long id,
        Long userId,
        Long friendId,
        String friendName,
        String friendImage,
        LocalDateTime createdAt) {
}
