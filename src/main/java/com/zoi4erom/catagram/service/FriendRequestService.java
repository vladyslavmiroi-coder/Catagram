package com.zoi4erom.catagram.service;

import com.zoi4erom.catagram.dto.friend.FriendshipCreateDTO;
import com.zoi4erom.catagram.dto.friendRequest.FriendRequestCreateDTO;
import com.zoi4erom.catagram.dto.friendRequest.FriendRequestReadDTO;
import com.zoi4erom.catagram.entity.FriendRequest;
import com.zoi4erom.catagram.entity.User;
import com.zoi4erom.catagram.mapper.FriendRequestMapper;
import com.zoi4erom.catagram.repository.FriendRequestRepository;
import com.zoi4erom.catagram.repository.FriendshipRepository;
import com.zoi4erom.catagram.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendRequestService implements CrudService<
        FriendRequestCreateDTO,
        FriendRequestCreateDTO,
        FriendRequestReadDTO,
        Long> {

        private final FriendRequestRepository friendRequestRepository;
        private final UserRepository userRepository;
        private final FriendRequestMapper friendRequestMapper;
        private final FriendshipService friendshipService;
        private final FriendshipRepository friendshipRepository;

        @Override
        public void create(FriendRequestCreateDTO createDTO) {
                Long senderId = createDTO.senderId();
                Long receiverId = createDTO.receiverId();

                if (senderId.equals(receiverId)) {
                        throw new IllegalArgumentException("Ви не можете надіслати запит самому собі");
                }

                Long firstId = Math.min(senderId, receiverId);
                Long secondId = Math.max(senderId, receiverId);
                if (friendshipRepository.existsByUserIdAndFriendId(firstId, secondId)) {
                        throw new IllegalStateException("Ви вже у списку друзів цього користувача");
                }

                if (friendRequestRepository.existsBetweenUsers(senderId, receiverId)) {
                        throw new IllegalStateException("Запит уже надіслано або очікує підтвердження");
                }

                User sender = userRepository.findById(senderId)
                        .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

                User receiver = userRepository.findById(receiverId)
                        .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

                FriendRequest friendRequest = FriendRequest.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .status("PENDING")
                        .createdAt(LocalDateTime.now())
                        .build();

                friendRequestRepository.save(friendRequest);
        }
        @Transactional
        public void acceptFriendRequest(Long friendRequestId) {
                FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId)
                        .orElseThrow(() -> new EntityNotFoundException("Запит на дружбу не знайдено"));

                friendshipService.create(new FriendshipCreateDTO(
                        friendRequest.getSender().getId(),
                        friendRequest.getReceiver().getId()
                ));

                friendRequestRepository.delete(friendRequest);
        }

        public List<FriendRequestReadDTO> findFriendRequestsByUserId(Long id) {
                return friendRequestRepository.findFriendRequestsByReceiverId(id).stream()
                        .map(friendRequestMapper::toDto)
                        .collect(Collectors.toList());
        }

        @Override
        public FriendRequestReadDTO findById(Long id) {
                return friendRequestRepository.findById(id)
                        .map(friendRequestMapper::toDto)
                        .orElseThrow(() -> new EntityNotFoundException("Friend request not found"));
        }

        @Override
        public FriendRequestReadDTO update(FriendRequestCreateDTO updateDTO) {
                throw new UnsupportedOperationException("Update is not supported");
        }

        @Override
        public void delete(Long id) {
                friendRequestRepository.deleteById(id);
        }
}