package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.dto.friend.FriendshipCreateDTO;
import com.zoi4erom.catagram.dto.friend.FriendshipReadDTO;
import com.zoi4erom.catagram.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendships")
@RequiredArgsConstructor
public class FriendshipController {

        private final FriendshipService friendshipService;

        @PostMapping
        public ResponseEntity<Void> createFriendship(
                @RequestBody FriendshipCreateDTO createDTO
        ) {
                friendshipService.create(createDTO);
                return ResponseEntity.ok().build();
        }

        @GetMapping("/{id}")
        public ResponseEntity<FriendshipReadDTO> getFriendshipById(
                @PathVariable Long id
        ) {
                return ResponseEntity.ok(friendshipService.findById(id));
        }

        @GetMapping("/user/{userId}")
        public ResponseEntity<List<FriendshipReadDTO>> getFriendshipsByUserId(
                @PathVariable Long userId
        ) {
                return ResponseEntity.ok(
                        friendshipService.findFriendshipByUserId(userId)
                );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteFriendship(
                @PathVariable Long id
        ) {
                friendshipService.delete(id);
                return ResponseEntity.ok().build();
        }
}