package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.dto.chat.ChatCreateDto;
import com.zoi4erom.catagram.dto.chat.ChatDTO;
import com.zoi4erom.catagram.dto.chat.ChatUpdateDto;
import com.zoi4erom.catagram.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

        private final ChatService chatService;

        @PostMapping
        public ResponseEntity<Void> createChat(@RequestBody ChatCreateDto createDto) {
                chatService.create(createDto);
                return ResponseEntity.ok().build();
        }

        @GetMapping("/{id}")
        public ResponseEntity<ChatDTO> getChatById(@PathVariable Long id) {
                return ResponseEntity.ok(chatService.findById(id));
        }

        @GetMapping
        public ResponseEntity<List<ChatDTO>> getAllChats() {
                return ResponseEntity.ok(chatService.getAllChats());
        }

        @PutMapping
        public ResponseEntity<ChatDTO> updateChat(@RequestBody ChatUpdateDto updateDto) {
                return ResponseEntity.ok(chatService.update(updateDto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
                chatService.delete(id);
                return ResponseEntity.ok().build();
        }
}