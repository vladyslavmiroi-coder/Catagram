package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
        List<Chat> findByMembersUserId(Long userId);

        List<Chat> findByNameContainingIgnoreCase(String chatName);
}
