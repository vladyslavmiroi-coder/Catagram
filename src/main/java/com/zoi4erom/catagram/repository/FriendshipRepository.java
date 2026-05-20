package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
}
