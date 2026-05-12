package com.zoi4erom.catagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequest {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "sender_id", nullable = false)
        private User sender;

        @ManyToOne
        @JoinColumn(name = "receiver_id", nullable = false)
        private User receiver;

        @Column(length = 20)
        private String status = "PENDING";

        @Column(name = "created_at")
        private LocalDateTime createdAt;
}