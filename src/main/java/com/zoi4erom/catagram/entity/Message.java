package com.zoi4erom.catagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "chat_id", nullable = false)
        private Chat chat;

        @ManyToOne
        @JoinColumn(name = "sender_id", nullable = false)
        private User sender;

        @Column(columnDefinition = "TEXT", nullable = false)
        private String content;

        @Column(name = "is_edited")
        private Boolean isEdited = false;

        @Column(name = "is_deleted")
        private Boolean isDeleted = false;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;
}