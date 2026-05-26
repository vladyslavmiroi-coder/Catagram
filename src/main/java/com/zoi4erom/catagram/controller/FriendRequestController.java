package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.dto.friendRequest.FriendRequestCreateDTO;
import com.zoi4erom.catagram.dto.friendRequest.FriendRequestReadDTO;
import com.zoi4erom.catagram.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendRequests")
@RequiredArgsConstructor
public class FriendRequestController {

        private final FriendRequestService friendRequestService;

        @PostMapping
        public ResponseEntity<Void> createFriendRequest(
                @RequestBody FriendRequestCreateDTO createDTO
        ) {
                friendRequestService.create(createDTO);
                return ResponseEntity.ok().build();
        }

        @GetMapping("/{id}")
        public ResponseEntity<FriendRequestReadDTO> getFriendRequestById(
                @PathVariable Long id
        ) {
                return ResponseEntity.ok(
                        friendRequestService.findById(id)
                );
        }

        @PatchMapping("/{id}/accept")
        public ResponseEntity<Void> acceptFriendRequest(
                @PathVariable Long id
        ) {
                friendRequestService.acceptFriendRequest(id);
                return ResponseEntity.ok().build();
        }

        @GetMapping("/user/{id}")
        public ResponseEntity<List<FriendRequestReadDTO>> getFriendRequestsByUserId(
                @PathVariable Long id
        ) {
                return ResponseEntity.ok(
                        friendRequestService.findFriendRequestsByUserId(id)
                );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteFriendRequest(
                @PathVariable Long id
        ) {
                friendRequestService.delete(id);
                return ResponseEntity.ok().build();
        }
}