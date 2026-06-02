package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.dto.chatMember.ChatMemberCreateDTO;
import com.zoi4erom.catagram.dto.chatMember.ChatMemberReadDTO;
import com.zoi4erom.catagram.dto.chatMember.MuteBanRequest;
import com.zoi4erom.catagram.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatMembers")
@RequiredArgsConstructor
public class ChatMemberController {

        private final ChatMemberService chatMemberService;

        @PostMapping
        public ResponseEntity<Void> createChatMember(@RequestBody ChatMemberCreateDTO createDTO) {

                chatMemberService.create(createDTO);

                return ResponseEntity.ok().build();
        }

        @GetMapping("/{id}")
        public ResponseEntity<ChatMemberReadDTO> getById(@PathVariable Long id) {

                return ResponseEntity.ok(chatMemberService.findById(id));
        }

        @GetMapping("/chat/{chatId}")
        public ResponseEntity<List<ChatMemberReadDTO>> getByChatId(@PathVariable Long chatId) {

                return ResponseEntity.ok(chatMemberService.findByChatId(chatId));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {

                chatMemberService.delete(id);

                return ResponseEntity.ok().build();
        }

        @DeleteMapping("/chat/{chatId}/user/{userId}")
        public ResponseEntity<Void> deleteByChatIdAndUserId(
                @PathVariable Long chatId,
                @PathVariable Long userId
        ) {

                chatMemberService.deleteByChatIdAndUserId(chatId, userId);

                return ResponseEntity.ok().build();
        }

        @PostMapping("/chat/{chatId}/user/{userId}/mute")
        public ResponseEntity<Void> muteMember(
                @PathVariable Long chatId,
                @PathVariable Long userId,
                @RequestBody MuteBanRequest request
        ) {
                chatMemberService.muteMember(chatId, userId, request.until(), request.reason());
                return ResponseEntity.ok().build();
        }

        @PostMapping("/chat/{chatId}/user/{userId}/ban")
        public ResponseEntity<Void> banMember(
                @PathVariable Long chatId,
                @PathVariable Long userId,
                @RequestBody MuteBanRequest request
        ) {
                chatMemberService.banMember(chatId, userId, request.until(), request.reason());
                return ResponseEntity.ok().build();
        }
}