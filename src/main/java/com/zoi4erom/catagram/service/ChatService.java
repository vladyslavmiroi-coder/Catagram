package com.zoi4erom.catagram.service;

import com.zoi4erom.catagram.dto.chat.ChatCreateDto;
import com.zoi4erom.catagram.dto.chat.ChatDTO;
import com.zoi4erom.catagram.dto.chat.ChatUpdateDto;
import com.zoi4erom.catagram.entity.Chat;
import com.zoi4erom.catagram.entity.User;
import com.zoi4erom.catagram.mapper.ChatMapper;
import com.zoi4erom.catagram.repository.ChatMemberRepository;
import com.zoi4erom.catagram.repository.ChatRepository;
import com.zoi4erom.catagram.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService implements CrudService<ChatCreateDto, ChatUpdateDto, ChatDTO, Long> {

        private final ChatRepository chatRepository;
        private final UserRepository userRepository;
        private final ChatMapper chatMapper;

        @Override
        public void create(ChatCreateDto dto) {

                User owner = userRepository.findById(dto.ownerId())
                        .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

                Chat chat = Chat.builder()
                        .name(dto.name())
                        .description(dto.description())
                        .avatarUrl(dto.avatarUrl())
                        .bannerUrl(dto.bannerUrl())
                        .visibilityType(
                                dto.visibilityType() != null
                                        ? dto.visibilityType()
                                        : "PRIVATE"
                        )
                        .languageCode(
                                dto.languageCode() != null
                                        ? dto.languageCode()
                                        : "en"
                        )
                        .owner(owner)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                chatRepository.save(chat);
        }

        @Override
        public ChatDTO findById(Long id) {
                Chat chat = chatRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
                return chatMapper.toDto(chat);
        }

        public List<ChatDTO> getAllChats() {
                return chatRepository.findAll().stream()
                        .map(chatMapper::toDto)
                        .toList();
        }

        @Override
        public ChatDTO update(ChatUpdateDto dto) {

                Chat chat = chatRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

                if (dto.name() != null) {
                        chat.setName(dto.name());
                }

                if (dto.description() != null) {
                        chat.setDescription(dto.description());
                }

                if (dto.avatarUrl() != null) {
                        chat.setAvatarUrl(dto.avatarUrl());
                }

                if (dto.bannerUrl() != null) {
                        chat.setBannerUrl(dto.bannerUrl());
                }

                if (dto.visibilityType() != null) {
                        chat.setVisibilityType(dto.visibilityType());
                }

                if (dto.languageCode() != null) {
                        chat.setLanguageCode(dto.languageCode());
                }

                chat.setUpdatedAt(LocalDateTime.now());

                Chat updated = chatRepository.save(chat);

                return chatMapper.toDto(updated);
        }

        @Override
        public void delete(Long id) {

                if (!chatRepository.existsById(id)) {
                        throw new EntityNotFoundException("Chat not found");
                }

                chatRepository.deleteById(id);
        }
}