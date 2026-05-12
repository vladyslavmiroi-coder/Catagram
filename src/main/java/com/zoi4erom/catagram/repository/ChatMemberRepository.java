package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberRepository extends JpaRepository<ChatMember,Long> {
}
