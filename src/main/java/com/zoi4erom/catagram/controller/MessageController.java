package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.dto.message.MessageCreateDto;
import com.zoi4erom.catagram.dto.message.MessageReadDto;
import com.zoi4erom.catagram.dto.message.MessageUpdateDto;
import com.zoi4erom.catagram.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

        private final MessageService messageService;
        private final SimpMessagingTemplate messagingTemplate;

        @MessageMapping("/chat/sendMessage")
        public void processMessage(@Payload MessageCreateDto dto) {
                MessageReadDto savedMessage = messageService.create(dto);

                messagingTemplate.convertAndSend("/topic/chat/" + dto.chatId(), savedMessage);
        }

        @GetMapping("/{id}")
        public ResponseEntity<MessageReadDto> getMessageById(@PathVariable Long id) {
                return ResponseEntity.ok(messageService.findById(id));
        }

        @PutMapping
        public ResponseEntity<MessageReadDto> updateMessage(@RequestBody MessageUpdateDto dto) {
                return ResponseEntity.ok(messageService.update(dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
                messageService.delete(id);
                return ResponseEntity.ok().build();
        }

        @GetMapping("/chat/{chatId}")
        public ResponseEntity<List<MessageReadDto>> getMessagesByChatId(@PathVariable Long chatId) {
                return ResponseEntity.ok(messageService.getByChatId(chatId));
        }
}