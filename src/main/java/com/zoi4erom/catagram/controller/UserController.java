package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.dto.user.UserCreateDTO;
import com.zoi4erom.catagram.dto.user.UserReadDTO;
import com.zoi4erom.catagram.dto.user.UserUpdateDTO;
import com.zoi4erom.catagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

        private final UserService userService;

        @PostMapping
        public ResponseEntity<Void> createUser(@RequestBody UserCreateDTO createDTO) {
                userService.create(createDTO);
                return ResponseEntity.ok().build();
        }

        @GetMapping("/{id}")
        public ResponseEntity<UserReadDTO> getUserById(@PathVariable Long id) {
                return ResponseEntity.ok(userService.findById(id));
        }

        @GetMapping("/search/byUsername")
        public ResponseEntity<List<UserReadDTO>> searchUsers(
                @RequestParam String username
        ) {
                return ResponseEntity.ok(
                        userService.findByUsernameContainingIgnoreCase(username)
                );
        }

        @GetMapping
        public ResponseEntity<List<UserReadDTO>> getAllUsers() {
                return ResponseEntity.ok(userService.findAll());
        }

        @PutMapping
        public ResponseEntity<UserReadDTO> updateUser(@RequestBody UserUpdateDTO updateDTO) {
                return ResponseEntity.ok(userService.update(updateDTO));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
                userService.delete(id);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/{id}/avatar")
        public ResponseEntity<Void> updateAvatar(@PathVariable Long id, @RequestPart("image") MultipartFile image) {
                userService.updateUserAvatar(id, image);
                return ResponseEntity.ok().build();
        }
}