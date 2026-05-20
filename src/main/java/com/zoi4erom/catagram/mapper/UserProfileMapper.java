package com.zoi4erom.catagram.mapper;

import com.zoi4erom.catagram.dto.userProfile.UserProfileDTO;
import com.zoi4erom.catagram.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileMapper implements Mapper<UserProfile, UserProfileDTO>{

        private final UserMapper userMapper;

        @Override
        public UserProfileDTO toDto(UserProfile entity) {
                return new UserProfileDTO(
                        entity.getId(),
                        userMapper.toDto(entity.getUser()),
                        entity.getEmail(),
                        entity.getBio(),
                        entity.getBirthDate(),
                        entity.getCountry(),
                        entity.getCity(),
                        entity.getCreatedAt()
                );
        }

        @Override
        public UserProfile toEntity(UserProfileDTO dto) {
                return null;
        }
}
