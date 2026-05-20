package com.zoi4erom.catagram.service;

import com.zoi4erom.catagram.dto.userProfile.UserProfileDTO;
import com.zoi4erom.catagram.entity.User;
import com.zoi4erom.catagram.entity.UserProfile;
import com.zoi4erom.catagram.mapper.UserProfileMapper;
import com.zoi4erom.catagram.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProfileService {

        private final UserProfileRepository userProfileRepository;
        private final UserProfileMapper userProfileMapper;

        public void createUserProfile(User user){
                userProfileRepository.save(
                        UserProfile.builder()
                                .user(user)
                                .build()
                );
        }

        public UserProfileDTO getUserProfileByUserId(Long userId){
                return userProfileMapper.toDto(
                        userProfileRepository.getUserProfileByUserId(userId)
                                .orElseThrow(() ->
                                        new RuntimeException("Profile not found")
                                )
                );
        }

        public UserProfileDTO updateUserProfile(Long userId, UserProfileDTO dto) {
                UserProfile profile =
                        userProfileRepository.getUserProfileByUserId(userId)
                                .orElseThrow(() ->
                                        new RuntimeException("Profile not found")
                                );

                if (dto.email() != null) {
                        profile.setEmail(dto.email());
                }

                if (dto.bio() != null) {
                        profile.setBio(dto.bio());
                }

                if (dto.birthDate() != null) {
                        profile.setBirthDate(dto.birthDate());
                }

                if (dto.country() != null) {
                        profile.setCountry(dto.country());
                }

                if (dto.city() != null) {
                        profile.setCity(dto.city());
                }

                UserProfile savedProfile =
                        userProfileRepository.save(profile);

                return userProfileMapper.toDto(savedProfile);
        }
}
