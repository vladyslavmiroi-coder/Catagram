package com.zoi4erom.catagram.mapper;

import com.zoi4erom.catagram.dto.chat.ChatDTO;
import com.zoi4erom.catagram.entity.Chat;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper implements Mapper<Chat, ChatDTO> {

        @Override
        public ChatDTO toDto(Chat chat) {
                if (chat == null) {
                        return null;
                }

                return new ChatDTO(
                        chat.getId(),
                        chat.getName(),
                        chat.getDescription(),
                        chat.getAvatarUrl(),
                        chat.getBannerUrl(),
                        chat.getVisibilityType(),
                        chat.getOwner() != null ? chat.getOwner().getId() : null,
                        chat.getOwner() != null ? chat.getOwner().getUsername() : null,
                        chat.getLanguageCode(),
                        chat.getCreatedAt(),
                        chat.getUpdatedAt(),
                        chat.getMembers() != null ? chat.getMembers().size() : 0,
                        chat.getMessages() != null ? chat.getMessages().size() : 0
                );
        }

        @Override
        public Chat toEntity(ChatDTO dto) {
                if (dto == null) {
                        return null;
                }

                return Chat.builder()
                        .id(dto.id())
                        .name(dto.name())
                        .description(dto.description())
                        .avatarUrl(dto.avatarUrl())
                        .bannerUrl(dto.bannerUrl())
                        .visibilityType(dto.visibilityType())
                        .languageCode(dto.languageCode())
                        .createdAt(dto.createdAt())
                        .updatedAt(dto.updatedAt())
                        .build();
        }
}
