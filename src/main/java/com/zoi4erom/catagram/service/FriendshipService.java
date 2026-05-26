package com.zoi4erom.catagram.service;

import com.zoi4erom.catagram.dto.friend.FriendshipCreateDTO;
import com.zoi4erom.catagram.dto.friend.FriendshipReadDTO;
import com.zoi4erom.catagram.entity.Friendship;
import com.zoi4erom.catagram.entity.User;
import com.zoi4erom.catagram.mapper.FriendshipMapper;
import com.zoi4erom.catagram.repository.FriendshipRepository;
import com.zoi4erom.catagram.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService implements CrudService<
        FriendshipCreateDTO,
        FriendshipCreateDTO,
        FriendshipReadDTO,
        Long> {

        private final FriendshipRepository friendshipRepository;
        private final UserRepository userRepository;
        private final FriendshipMapper friendshipMapper;

        @Override
        public void create(FriendshipCreateDTO createDTO) {
                Long firstId = Math.min(createDTO.userId(), createDTO.friendId());
                Long secondId = Math.max(createDTO.userId(), createDTO.friendId());

                if (friendshipRepository.existsByUserIdAndFriendId(firstId, secondId)) {
                        throw new IllegalStateException("Користувачі вже є друзями");
                }

                User user = userRepository.findById(firstId)
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));

                User friend = userRepository.findById(secondId)
                        .orElseThrow(() -> new EntityNotFoundException("Friend not found"));

                Friendship friendship = Friendship.builder()
                        .user(user)
                        .friend(friend)
                        .createdAt(LocalDateTime.now())
                        .build();

                friendshipRepository.save(friendship);
        }

        public List<FriendshipReadDTO> findFriendshipByUserId(Long userId) {
                return friendshipRepository.findAllByAnyUserId(userId).stream()
                        .map(f -> {
                                User friendEntity = f.getUser().getId().equals(userId) ? f.getFriend() : f.getUser();

                                return new FriendshipReadDTO(
                                        f.getId(),
                                        userId,
                                        friendEntity.getId(),
                                        friendEntity.getUsername(),
                                        friendEntity.getAvatarUrl(),
                                        f.getCreatedAt()
                                );
                        })
                        .collect(Collectors.toList());
        }

        @Override
        public FriendshipReadDTO findById(Long id) {
                return friendshipRepository.findById(id)
                        .map(friendshipMapper::toDto)
                        .orElseThrow(() -> new EntityNotFoundException("Friendship not found"));
        }

        @Override
        public FriendshipReadDTO update(FriendshipCreateDTO updateDTO) {
                throw new UnsupportedOperationException("Update is not supported");
        }

        @Override
        public void delete(Long id) {
                friendshipRepository.deleteById(id);
        }
}