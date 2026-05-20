package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
}
