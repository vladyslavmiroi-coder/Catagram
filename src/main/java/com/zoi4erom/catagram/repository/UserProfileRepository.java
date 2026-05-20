package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

        Optional<UserProfile> getUserProfileByUserId(Long userId);
}
