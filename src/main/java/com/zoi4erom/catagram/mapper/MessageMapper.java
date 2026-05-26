package com.zoi4erom.catagram.mapper;

import com.zoi4erom.catagram.dto.message.MessageReadDto;
import com.zoi4erom.catagram.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements Mapper<Message, MessageReadDto> {

        @Override
        public MessageReadDto toDto(Message entity) {
                return new MessageReadDto(
                        entity.getId(),
                        entity.getChat().getId(),
                        entity.getSender().getId(),
                        entity.getSender().getUsername(),
                        entity.getSender().getAvatarUrl(),
                        entity.getContent(),
                        entity.getIsEdited(),
                        entity.getIsDeleted(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                );
        }

        @Override
        public Message toEntity(MessageReadDto dto) {
                throw new UnsupportedOperationException("Use create/update specific methods");
        }
}