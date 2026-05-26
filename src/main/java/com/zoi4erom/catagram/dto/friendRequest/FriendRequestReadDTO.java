package com.zoi4erom.catagram.dto.friendRequest;

import java.time.LocalDateTime;

public record FriendRequestReadDTO(Long id, Long senderId, String senderName, String senderImageUrl, Long receiverId, String status, LocalDateTime createdAt) {
}
