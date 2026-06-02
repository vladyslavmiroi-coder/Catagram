package com.zoi4erom.catagram.service;

import com.zoi4erom.catagram.dto.chatMember.ChatMemberCreateDTO;
import com.zoi4erom.catagram.dto.chatMember.ChatMemberReadDTO;
import com.zoi4erom.catagram.entity.Chat;
import com.zoi4erom.catagram.entity.ChatMember;
import com.zoi4erom.catagram.entity.User;
import com.zoi4erom.catagram.mapper.ChatMemberMapper;
import com.zoi4erom.catagram.repository.ChatMemberRepository;
import com.zoi4erom.catagram.repository.ChatRepository;
import com.zoi4erom.catagram.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMemberService implements CrudService<ChatMemberCreateDTO, ChatMemberReadDTO, ChatMemberReadDTO, Long> {

        private final ChatMemberRepository chatMemberRepository;
        private final ChatMemberMapper chatMemberMapper;
        private final ChatRepository chatRepository;
        private final UserRepository userRepository;

        @Override
        public void create(ChatMemberCreateDTO createDTO) {
                Chat chat = chatRepository.findById(createDTO.chatId())
                        .orElseThrow(() -> new RuntimeException("Chat not found"));

                User user = userRepository.findById(createDTO.userId())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                boolean exists = chatMemberRepository.existsByChatIdAndUserId(
                        createDTO.chatId(),
                        createDTO.userId()
                );

                if (exists) {
                        throw new RuntimeException("User already exists in chat");
                }

                ChatMember chatMember = ChatMember.builder()
                        .chat(chat)
                        .user(user)
                        .joinedAt(LocalDateTime.now())
                        .mutedReason(null)
                        .bannedReason(null)
                        .bannedUntil(null)
                        .mutedUntil(null)
                        .build();

                chatMemberRepository.save(chatMember);
        }

        @Override
        public ChatMemberReadDTO findById(Long id) {

                return chatMemberRepository.findById(id)
                        .map(chatMemberMapper::toDto)
                        .orElseThrow(() -> new RuntimeException("ChatMember not found"));
        }

        public List<ChatMemberReadDTO> findByChatId(Long chatId) {

                if (!chatRepository.existsById(chatId)) {
                        throw new RuntimeException("Chat not found");
                }

                return chatMemberRepository.findByChatId(chatId)
                        .stream()
                        .map(chatMemberMapper::toDto)
                        .toList();
        }

        @Override
        public ChatMemberReadDTO update(ChatMemberReadDTO updateDTO) {

                ChatMember chatMember = chatMemberRepository.findById(updateDTO.id())
                        .orElseThrow(() -> new RuntimeException("ChatMember not found"));

                return chatMemberMapper.toDto(chatMemberRepository.save(chatMember));
        }

        @Override
        public void delete(Long id) {

                ChatMember chatMember = chatMemberRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("ChatMember not found"));

                chatMemberRepository.delete(chatMember);
        }

        @Transactional
        public void muteMember(Long chatId, Long userId, LocalDateTime until, String reason) {
                ChatMember member = getMember(chatId, userId);
                member.setMutedUntil(until);
                member.setMutedReason(reason);
        }

        @Transactional
        public void banMember(Long chatId, Long userId, LocalDateTime until, String reason) {
                ChatMember member = getMember(chatId, userId);
                member.setBannedUntil(until);
                member.setBannedReason(reason);
        }

        public boolean isMuted(ChatMember member) {
                return member.getMutedUntil() != null && member.getMutedUntil().isAfter(LocalDateTime.now());
        }

        public boolean isBanned(ChatMember member) {
                return member.getBannedUntil() != null && member.getBannedUntil().isAfter(LocalDateTime.now());
        }

        private ChatMember getMember(Long chatId, Long userId) {
                return chatMemberRepository.findByChatIdAndUserId(chatId, userId)
                        .orElseThrow(() -> new RuntimeException("Учасника не знайдено"));
        }

        public void deleteByChatIdAndUserId(Long chatId, Long userId) {

                boolean exists = chatMemberRepository.existsByChatIdAndUserId(chatId, userId);

                if (!exists) {
                        throw new RuntimeException("User is not a member of this chat");
                }

                chatMemberRepository.deleteByChatIdAndUserId(chatId, userId);
        }
}