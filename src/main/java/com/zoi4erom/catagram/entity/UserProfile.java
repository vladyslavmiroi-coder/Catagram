package com.zoi4erom.catagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserProfile {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "user_id", nullable = false, unique = true)
        private User user;

        @Column(unique = true, length = 100)
        private String email;

        @Column(name = "avatar_url", columnDefinition = "TEXT")
        private String avatarUrl;

        @Column(columnDefinition = "TEXT")
        private String bio;

        @Column(name = "birth_date")
        private LocalDate birthDate;

        @Column(length = 100)
        private String country;

        @Column(length = 100)
        private String city;

        @Column(name = "language_code", nullable = false, length = 10)
        private String languageCode = "en";

        @Column(name = "created_at")
        private LocalDateTime createdAt;
}