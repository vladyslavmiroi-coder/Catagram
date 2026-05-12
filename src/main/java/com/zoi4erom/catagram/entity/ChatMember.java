package com.zoi4erom.catagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMember {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "chat_id", nullable = false)
        private Chat chat;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "role_id")
        private ChatRole role;

        @Column(name = "joined_at")
        private LocalDateTime joinedAt;

        @Column(name = "is_muted")
        private Boolean isMuted = false;

        @Column(name = "banned_until")
        private LocalDateTime bannedUntil;
}