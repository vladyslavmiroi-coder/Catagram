package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.config.CustomUserDetails;
import com.zoi4erom.catagram.dto.message.MessageCreateDto;
import com.zoi4erom.catagram.dto.message.MessageReadDto;
import com.zoi4erom.catagram.dto.message.MessageUpdateDto;
import com.zoi4erom.catagram.entity.ChatMember;
import com.zoi4erom.catagram.entity.User;
import com.zoi4erom.catagram.repository.ChatMemberRepository;
import com.zoi4erom.catagram.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

        private final MessageService messageService;
        private final SimpMessagingTemplate messagingTemplate;
        private final TranslationService translationService;
        private final ChatMemberRepository chatMemberRepository;


        @MessageMapping("/chat/sendMessage")
        public void processMessage(@Payload MessageCreateDto dto) {
                messageService.validateMessageSending(dto.chatId(), dto.senderId());

                MessageReadDto savedMessage = messageService.create(dto);
                sendToAllChatMembers(savedMessage);
        }

        @GetMapping("/chat/{chatId}")
        public ResponseEntity<List<MessageReadDto>> getMessagesByChatId(
                @PathVariable Long chatId,
                @AuthenticationPrincipal CustomUserDetails userDetails) {

                if (userDetails == null) return ResponseEntity.status(401).build();

                String targetLang = userDetails.getLanguageCode();
                List<MessageReadDto> messages = messageService.getByChatId(chatId);

                List<MessageReadDto> translatedMessages = messages.stream()
                        .map(msg -> localizeMessage(msg, targetLang))
                        .toList();

                return ResponseEntity.ok(translatedMessages);
        }

        @PutMapping
        public ResponseEntity<MessageReadDto> updateMessage(@RequestBody MessageUpdateDto dto) {
                MessageReadDto updated = messageService.update(dto);
                sendToAllChatMembers(updated);
                return ResponseEntity.ok(updated);
        }

        @PatchMapping("/{id}/soft-delete")
        public ResponseEntity<Void> softDeleteMessage(@PathVariable Long id) {
                messageService.softDelete(id);
                sendToAllChatMembers(messageService.findById(id));
                return ResponseEntity.ok().build();
        }

        private void sendToAllChatMembers(MessageReadDto message) {
                List<ChatMember> members = chatMemberRepository.findByChatId(message.chatId());
                Map<String, String> translationCache = new HashMap<>();

                for (ChatMember member : members) {
                        User recipient = member.getUser();
                        String targetLang = recipient.getLanguageCode();

                        String content = translationCache.computeIfAbsent(targetLang,
                                lang -> getTranslatedContent(message, lang));

                        messagingTemplate.convertAndSendToUser(
                                recipient.getUsername(),
                                "/queue/messages",
                                message.withContent(content)
                        );
                }
        }

        private String getTranslatedContent(MessageReadDto message, String targetLang) {
                if (message.senderLanguageCode().equalsIgnoreCase(targetLang)) {
                        return message.content();
                }
                return translationService.getOrTranslate(message.id(), targetLang);
        }

        private MessageReadDto localizeMessage(MessageReadDto msg, String targetLang) {
                return msg.withContent(getTranslatedContent(msg, targetLang));
        }
}