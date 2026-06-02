package com.zoi4erom.catagram.service;

import com.zoi4erom.catagram.dto.chat.ChatCreateDto;
import com.zoi4erom.catagram.dto.chat.ChatDTO;
import com.zoi4erom.catagram.dto.chat.ChatUpdateDto;
import com.zoi4erom.catagram.dto.chatMember.ChatMemberCreateDTO;
import com.zoi4erom.catagram.entity.Chat;
import com.zoi4erom.catagram.entity.User;
import com.zoi4erom.catagram.mapper.ChatMapper;
import com.zoi4erom.catagram.repository.ChatRepository;
import com.zoi4erom.catagram.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService implements CrudService<ChatCreateDto, ChatUpdateDto, ChatDTO, Long> {

        private final ChatRepository chatRepository;
        private final UserRepository userRepository;
        private final ChatMapper chatMapper;
        private final ChatMemberService chatMemberService;
        private final ImageService imageService;

        public ChatDTO create(ChatCreateDto dto, MultipartFile file) {
                User owner = userRepository.findById(dto.ownerId())
                        .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

                String avatarUrl = dto.avatarUrl();
                if (file != null && !file.isEmpty()) {
                        avatarUrl = imageService.uploadImage(file);
                }

                Chat chat = Chat.builder()
                        .name(dto.name())
                        .description(dto.description())
                        .avatarUrl(avatarUrl)
                        .visibilityType(dto.visibilityType() != null ? dto.visibilityType() : "PUBLIC")
                        .languageCode(dto.languageCode() != null ? dto.languageCode() : "en")
                        .owner(owner)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                Chat savedChat = chatRepository.save(chat);

                chatMemberService.create(new ChatMemberCreateDTO(
                        savedChat.getOwner().getId(),
                        savedChat.getId()
                ));

                return chatMapper.toDto(savedChat);
        }

        @Override
        public void create(ChatCreateDto createDTO) {

        }

        @Override
        public ChatDTO findById(Long id) {

                Chat chat = chatRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

                return chatMapper.toDto(chat);
        }

        @Override
        public ChatDTO update(ChatUpdateDto updateDTO) {
                return null;
        }

        public List<ChatDTO> getAllChats() {

                return chatRepository.findAll().stream()
                        .map(chatMapper::toDto)
                        .toList();
        }

        public List<ChatDTO> getChatsByUserId(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new EntityNotFoundException("User not found");
                }

                return chatRepository.findByMembersUserId(userId)
                        .stream()
                        .map(chatMapper::toDto)
                        .toList();
        }

        public List<ChatDTO> searchChatsByName(String chatName) {

                return chatRepository.findByNameContainingIgnoreCase(chatName)
                        .stream()
                        .map(chatMapper::toDto)
                        .toList();
        }

        public ChatDTO update(ChatUpdateDto dto, MultipartFile image) {

                Chat chat = chatRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

                if (dto.name() != null) {
                        chat.setName(dto.name());
                }

                if (dto.description() != null) {
                        chat.setDescription(dto.description());
                }

                if (chat.getAvatarUrl() != null && !chat.getAvatarUrl().isBlank()) {
                        imageService.deleteImage(chat.getAvatarUrl());

                        chat.setAvatarUrl(imageService.uploadImage(image));
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