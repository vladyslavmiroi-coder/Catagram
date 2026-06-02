package com.zoi4erom.catagram.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message_translations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageTranslation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "message_id")
        private Message message;

        private String languageCode;

        @Column(columnDefinition = "TEXT")
        private String content;
}