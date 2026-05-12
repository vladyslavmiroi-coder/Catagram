package com.zoi4erom.catagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRole {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "chat_id", nullable = false)
        private Chat chat;

        @Column(name = "role_name", nullable = false, length = 50)
        private String roleName;

        @Column(name = "role_color", length = 20)
        private String roleColor;

        @Column(name = "is_default")
        private Boolean isDefault = false;

        @Column(name = "can_delete_messages")
        private Boolean canDeleteMessages = false;

        @Column(name = "can_ban_users")
        private Boolean canBanUsers = false;

        @Column(name = "can_invite_users")
        private Boolean canInviteUsers = false;

        @Column(name = "can_manage_roles")
        private Boolean canManageRoles = false;

        @Column(name = "can_manage_chat")
        private Boolean canManageChat = false;

        @Column(name = "can_pin_messages")
        private Boolean canPinMessages = false;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Builder.Default
        @OneToMany(mappedBy = "role")
        private List<ChatMember> members = new ArrayList<>();
}