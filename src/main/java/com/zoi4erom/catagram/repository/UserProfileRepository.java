package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
