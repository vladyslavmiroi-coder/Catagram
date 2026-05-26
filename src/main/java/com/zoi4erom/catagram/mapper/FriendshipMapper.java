package com.zoi4erom.catagram.mapper;

import com.zoi4erom.catagram.dto.friend.FriendshipReadDTO;
import com.zoi4erom.catagram.entity.Friendship;
import org.springframework.stereotype.Component;

@Component
public class FriendshipMapper implements Mapper<Friendship, FriendshipReadDTO> {

        @Override
        public FriendshipReadDTO toDto(Friendship entity) {
                if (entity == null) {
                        return null;
                }

                return new FriendshipReadDTO(
                        entity.getId(),
                        entity.getUser().getId(),
                        entity.getFriend().getId(),
                        entity.getFriend().getUsername(),
                        entity.getFriend().getAvatarUrl(),
                        entity.getCreatedAt()
                );
        }

        @Override
        public Friendship toEntity(FriendshipReadDTO dto) {
                if (dto == null) {
                        return null;
                }

                return Friendship.builder()
                        .id(dto.id())
                        .createdAt(dto.createdAt())
                        .build();
        }
}