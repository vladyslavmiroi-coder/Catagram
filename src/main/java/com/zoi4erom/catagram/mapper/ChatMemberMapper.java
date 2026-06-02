package com.zoi4erom.catagram.mapper;

import com.zoi4erom.catagram.dto.chatMember.ChatMemberReadDTO;
import com.zoi4erom.catagram.entity.ChatMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMemberMapper implements Mapper<ChatMember, ChatMemberReadDTO> {
        private final ChatMapper chatMapper;
        private final UserMapper userMapper;

        @Override
        public ChatMemberReadDTO toDto(ChatMember entity) {
                return new ChatMemberReadDTO(
                        entity.getId(),
                        chatMapper.toDto(entity.getChat()),
                        userMapper.toDto(entity.getUser()),
                        entity.getJoinedAt(),
                        entity.getMutedUntil(),
                        entity.getMutedReason(),
                        entity.getBannedUntil(),
                        entity.getBannedReason()
                );
        }

        @Override
        public ChatMember toEntity(ChatMemberReadDTO dto) {
                return null;
        }
}
