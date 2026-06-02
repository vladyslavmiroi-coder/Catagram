package com.zoi4erom.catagram.dto.chatMember;

import com.zoi4erom.catagram.dto.chat.ChatDTO;
import com.zoi4erom.catagram.dto.user.UserReadDTO;

import java.time.LocalDateTime;

public record ChatMemberReadDTO(
        Long id,
        ChatDTO chat,
        UserReadDTO userReadDTO,
        LocalDateTime joinedAt,
        LocalDateTime mutedUntil,
        String mutedReason,
        LocalDateTime bannedUntil,
        String bannedReason
) {}