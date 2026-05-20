package com.zoi4erom.catagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, length = 50)
        private String username;

        @Column(name = "phone_number", nullable = false, unique = true, length = 20)
        private String phoneNumber;

        @Column(nullable = false, length = 255)
        private String password;

        @Column(name = "avatar_url", columnDefinition = "TEXT")
        private String avatarUrl;

        @Column(name = "language_code", nullable = false, length = 10)
        private String languageCode = "en";

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        private UserProfile profile;

        @Builder.Default
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<Role> roles = new HashSet<>();
}