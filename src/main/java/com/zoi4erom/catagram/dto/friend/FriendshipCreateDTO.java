package com.zoi4erom.catagram.dto.friend;

public record FriendshipCreateDTO(
        Long userId,
        Long friendId
) {
}