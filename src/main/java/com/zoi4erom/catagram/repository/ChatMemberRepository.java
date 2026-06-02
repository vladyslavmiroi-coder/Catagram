package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.Chat;
import com.zoi4erom.catagram.entity.ChatMember;
import com.zoi4erom.catagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMemberRepository extends JpaRepository<ChatMember,Long> {

        List<ChatMember> findByChatId(Long chatId);

        boolean existsByChatIdAndUserId(Long chatId, Long userId);

        void deleteByChatIdAndUserId(Long chatId, Long userId);

        Optional<ChatMember> findByChatIdAndUserId(Long chatId, Long userId);
}
