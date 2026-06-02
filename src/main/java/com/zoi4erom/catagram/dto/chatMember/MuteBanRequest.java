package com.zoi4erom.catagram.dto.chatMember;

import java.time.LocalDateTime;

public record MuteBanRequest(
        LocalDateTime until,
        String reason
) {}