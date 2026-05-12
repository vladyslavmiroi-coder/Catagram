package com.zoi4erom.catagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 100)
        private String name;

        @Column(columnDefinition = "TEXT")
        private String description;

        @Column(name = "avatar_url", columnDefinition = "TEXT")
        private String avatarUrl;

        @Column(name = "banner_url", columnDefinition = "TEXT")
        private String bannerUrl;

        @Column(name = "visibility_type", nullable = false, length = 20)
        private String visibilityType = "PRIVATE";

        @ManyToOne
        @JoinColumn(name = "owner_id", nullable = false)
        private User owner;

        @Column(name = "language_code", length = 10)
        private String languageCode = "en";

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Builder.Default
        @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
        private List<Message> messages = new ArrayList<>();

        @Builder.Default
        @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
        private List<ChatMember> members = new ArrayList<>();

        @Builder.Default
        @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
        private List<ChatRole> roles = new ArrayList<>();
}