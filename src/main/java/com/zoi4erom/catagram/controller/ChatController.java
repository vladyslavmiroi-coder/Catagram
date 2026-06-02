package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.dto.chat.ChatCreateDto;
import com.zoi4erom.catagram.dto.chat.ChatDTO;
import com.zoi4erom.catagram.dto.chat.ChatUpdateDto;
import com.zoi4erom.catagram.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

        private final ChatService chatService;

        @PostMapping(consumes = {"multipart/form-data"})
        public ResponseEntity<ChatDTO> createChat(
                @RequestPart("chat") ChatCreateDto createDto,
                @RequestPart(value = "avatarFile", required = false) MultipartFile file) {

                return ResponseEntity.ok(chatService.create(createDto, file));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ChatDTO> getChatById(@PathVariable Long id) {

                return ResponseEntity.ok(chatService.findById(id));
        }

        @GetMapping
        public ResponseEntity<List<ChatDTO>> getAllChats() {

                return ResponseEntity.ok(chatService.getAllChats());
        }

        @GetMapping("/user/{userId}")
        public ResponseEntity<List<ChatDTO>> getChatsByUserId(@PathVariable Long userId) {

                return ResponseEntity.ok(chatService.getChatsByUserId(userId));
        }

        @GetMapping("/search")
        public ResponseEntity<List<ChatDTO>> searchChats(
                @RequestParam String name
        ) {

                return ResponseEntity.ok(chatService.searchChatsByName(name));
        }

        @PutMapping(consumes = {"multipart/form-data"})
        public ResponseEntity<ChatDTO> updateChat(
                @RequestPart("chat") ChatUpdateDto updateDto,
                @RequestPart(value = "avatarFile", required = false) MultipartFile file) {

                return ResponseEntity.ok(chatService.update(updateDto, file));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteChat(@PathVariable Long id) {

                chatService.delete(id);

                return ResponseEntity.ok().build();
        }
}