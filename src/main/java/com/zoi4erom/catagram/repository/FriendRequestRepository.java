package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

        List<FriendRequest> findFriendRequestsByReceiverId(Long id);

        @Query("SELECT COUNT(fr) > 0 FROM FriendRequest fr " +
                "WHERE (fr.sender.id = :id1 AND fr.receiver.id = :id2) " +
                "OR (fr.sender.id = :id2 AND fr.receiver.id = :id1) " +
                "AND fr.status = 'PENDING'")
        boolean existsBetweenUsers(Long id1, Long id2);
}
