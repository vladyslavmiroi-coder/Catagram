package com.zoi4erom.catagram.mapper;

import com.zoi4erom.catagram.dto.friendRequest.FriendRequestReadDTO;
import com.zoi4erom.catagram.entity.FriendRequest;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestMapper implements Mapper<FriendRequest, FriendRequestReadDTO> {

        @Override
        public FriendRequestReadDTO toDto(FriendRequest entity) {
                if (entity == null) {
                        return null;
                }

                return new FriendRequestReadDTO(
                        entity.getId(),
                        entity.getSender().getId(),
                        entity.getSender().getUsername(),
                        entity.getSender().getAvatarUrl(),
                        entity.getReceiver().getId(),
                        entity.getStatus(),
                        entity.getCreatedAt()
                );
        }

        @Override
        public FriendRequest toEntity(FriendRequestReadDTO dto) {
                if (dto == null) {
                        return null;
                }

                return FriendRequest.builder()
                        .id(dto.id())
                        .status(dto.status())
                        .createdAt(dto.createdAt())
                        .build();
        }
}