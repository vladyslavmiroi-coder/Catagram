package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

        @Query("SELECT f FROM Friendship f WHERE f.user.id = :userId OR f.friend.id = :userId")
        List<Friendship> findAllByAnyUserId(Long userId);

        boolean existsByUserIdAndFriendId(Long userId, Long friendId);
}
