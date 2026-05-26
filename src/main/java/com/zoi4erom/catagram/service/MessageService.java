package com.zoi4erom.catagram.service;

import com.zoi4erom.catagram.dto.message.MessageCreateDto;
import com.zoi4erom.catagram.dto.message.MessageReadDto;
import com.zoi4erom.catagram.dto.message.MessageUpdateDto;
import com.zoi4erom.catagram.entity.Chat;
import com.zoi4erom.catagram.entity.Message;
import com.zoi4erom.catagram.entity.User;
import com.zoi4erom.catagram.mapper.MessageMapper;
import com.zoi4erom.catagram.repository.ChatRepository;
import com.zoi4erom.catagram.repository.MessageRepository;
import com.zoi4erom.catagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

        private final MessageRepository messageRepository;
        private final ChatRepository chatRepository;
        private final UserRepository userRepository;
        private final MessageMapper messageMapper;

        public MessageReadDto create(MessageCreateDto dto) {

                Chat chat = chatRepository.findById(dto.chatId())
                        .orElseThrow(() -> new RuntimeException("Chat not found"));

                User sender = userRepository.findById(dto.senderId())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                Message message = Message.builder()
                        .chat(chat)
                        .sender(sender)
                        .content(dto.content())
                        .build();

                message.setCreatedAt(LocalDateTime.now());
                message.setUpdatedAt(LocalDateTime.now());

                return messageMapper.toDto(messageRepository.save(message));
        }

        public MessageReadDto findById(Long id) {
                return messageRepository.findById(id)
                        .map(messageMapper::toDto)
                        .orElseThrow(() -> new RuntimeException("Message not found"));
        }

        public MessageReadDto update(MessageUpdateDto dto) {

                Message message = messageRepository.findById(dto.id())
                        .orElseThrow(() -> new RuntimeException("Message not found"));

                message.setUpdatedAt(LocalDateTime.now());

                return messageMapper.toDto(messageRepository.save(message));
        }

        public void delete(Long id) {

                Message message = messageRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Message not found"));

                message.setIsDeleted(true);
                message.setUpdatedAt(LocalDateTime.now());

                messageRepository.save(message);
        }

        public List<MessageReadDto> getByChatId(Long chatId) {
                return messageRepository.findByChatId(chatId)
                        .stream()
                        .map(messageMapper::toDto)
                        .toList();
        }
}