package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
