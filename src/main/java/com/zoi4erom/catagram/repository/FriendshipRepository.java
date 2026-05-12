package com.zoi4erom.catagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<FriendRequest, Long> {
}
