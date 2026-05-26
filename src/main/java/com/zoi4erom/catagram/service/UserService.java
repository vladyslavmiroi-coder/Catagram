package com.zoi4erom.catagram.service;

import com.zoi4erom.catagram.dto.user.UserCreateDTO;
import com.zoi4erom.catagram.dto.user.UserReadDTO;
import com.zoi4erom.catagram.dto.user.UserUpdateDTO;
import com.zoi4erom.catagram.entity.Role;
import com.zoi4erom.catagram.entity.User;
import com.zoi4erom.catagram.mapper.UserMapper;
import com.zoi4erom.catagram.repository.RoleRepository;
import com.zoi4erom.catagram.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements CrudService<UserCreateDTO, UserUpdateDTO, UserReadDTO, Long> {

        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final ImageService imageService;
        private final PasswordEncoder passwordEncoder;
        private final RoleRepository roleRepository;
        private final UserProfileService userProfileService;

        @Override
        public void create(UserCreateDTO createDTO) {

                if (userRepository.existsByUsername(createDTO.username())) {
                        throw new RuntimeException("Username already exists");
                }

                Role userRole = roleRepository.findByName("USER")
                        .orElseThrow(() ->
                                new RuntimeException("Role USER not found")
                        );

                User user = userRepository.save(
                        User.builder()
                                .username(createDTO.username())
                                .password(
                                        passwordEncoder.encode(
                                                createDTO.password()
                                        )
                                )
                                .phoneNumber(createDTO.phoneNumber())
                                .languageCode("en")
                                .roles(Set.of(userRole))
                                .build()
                );

                userProfileService.createUserProfile(user);
        }

        @Override
        public UserReadDTO findById(Long id) {
                User user = userRepository.findById(id).orElseThrow(
                        () -> new RuntimeException("User not found"));

                return userMapper.toDto(user);
        }

        public List<UserReadDTO> findByUsernameContainingIgnoreCase(String username){
                return userRepository.findByUsernameContainingIgnoreCase(username).stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toList());
        }

        public List<UserReadDTO> findAll() {
                return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
        }

        public UserReadDTO findByUsername(String username) {
                User user = userRepository.findByUsername(username).orElseThrow(
                        () -> new RuntimeException("User not found"));

                return userMapper.toDto(user);
        }

        @Override
        public UserReadDTO update(UserUpdateDTO updateDTO) {
                User user = userRepository.findById(updateDTO.id()).orElseThrow(
                        () -> new RuntimeException("User not found"));

                user.setUsername(updateDTO.username());
                user.setPhoneNumber(updateDTO.phoneNumber());
                user.setLanguageCode(updateDTO.languageCode());

                User updatedUser = userRepository.save(user);

                return userMapper.toDto(updatedUser);
        }

        @Override
        public void delete(Long id) {
                if (!userRepository.existsById(id)) {
                        throw new RuntimeException("User not found");
                }

                userRepository.deleteById(id);
        }

        public void updateUserAvatar(Long userId, MultipartFile image) {
                User user = userRepository.findById(userId).orElseThrow(
                        () -> new RuntimeException("User not found"));

                if (user.getAvatarUrl() != null && !user.getAvatarUrl().isBlank()) {
                        imageService.deleteImage(user.getAvatarUrl());
                }

                user.setAvatarUrl(imageService.uploadImage(image));

                userRepository.save(user);
        }
}