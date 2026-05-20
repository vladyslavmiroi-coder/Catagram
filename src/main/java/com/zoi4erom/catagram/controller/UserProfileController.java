package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.dto.userProfile.UserProfileDTO;
import com.zoi4erom.catagram.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

        private final UserProfileService userProfileService;

        @GetMapping("/{id}")
        public ResponseEntity<UserProfileDTO> getUserById(@PathVariable Long id) {
                return ResponseEntity.ok(
                        userProfileService.getUserProfileByUserId(id)
                );
        }

        @PatchMapping("/{userId}")
        public ResponseEntity<UserProfileDTO> updateProfile(
                @PathVariable Long userId,
                @RequestBody UserProfileDTO dto
        ) {
                return ResponseEntity.ok(
                        userProfileService.updateUserProfile(userId, dto)
                );
        }
}
