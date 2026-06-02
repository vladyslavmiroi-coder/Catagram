package com.zoi4erom.catagram.mapper;

import com.zoi4erom.catagram.dto.user.UserReadDTO;
import com.zoi4erom.catagram.entity.Role;
import com.zoi4erom.catagram.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserReadDTO> {

        @Override
        public UserReadDTO toDto(User entity) {

                return new UserReadDTO(
                        entity.getId(),
                        entity.getUsername(),
                        entity.getPhoneNumber(),
                        entity.getAvatarUrl(),
                        entity.getLanguageCode(),
                        entity.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
                );
        }

        @Override
        public User toEntity(UserReadDTO dto) {

                return User.builder()
                        .id(dto.id())
                        .username(dto.username())
                        .phoneNumber(dto.phoneNumber())
                        .avatarUrl(dto.avatarUrl())
                        .languageCode(dto.languageCode())
                        .build();
        }
}