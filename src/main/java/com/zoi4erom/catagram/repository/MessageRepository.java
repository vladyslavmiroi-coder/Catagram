package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
