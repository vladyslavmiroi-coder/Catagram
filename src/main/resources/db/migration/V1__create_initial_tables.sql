CREATE TABLE users
(
    id           BIGSERIAL PRIMARY KEY,

    username     VARCHAR(50)  NOT NULL UNIQUE,
    phone_number VARCHAR(20)  NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL
);

CREATE TABLE user_profiles
(
    id            BIGSERIAL PRIMARY KEY,

    user_id       BIGINT      NOT NULL UNIQUE,

    email         VARCHAR(100) UNIQUE,
    avatar_url    TEXT,
    bio           TEXT,
    birth_date    DATE,
    country       VARCHAR(100),
    city          VARCHAR(100),
    language_code VARCHAR(10) NOT NULL DEFAULT 'en',

    created_at    TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_profiles_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE roles
(
    id BIGSERIAL PRIMARY KEY,

    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
            REFERENCES roles(id)
            ON DELETE CASCADE
);

CREATE TABLE friendships
(
    id         BIGSERIAL PRIMARY KEY,

    user_id    BIGINT NOT NULL,
    friend_id  BIGINT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_friendships_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_friendships_friend
        FOREIGN KEY (friend_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT chk_not_same_user
        CHECK (user_id <> friend_id)
);

CREATE TABLE friend_requests
(
    id          BIGSERIAL PRIMARY KEY,

    sender_id   BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,

    status      VARCHAR(20) DEFAULT 'PENDING',

    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_friend_requests_sender
        FOREIGN KEY (sender_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_friend_requests_receiver
        FOREIGN KEY (receiver_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE chats
(
    id                     BIGSERIAL PRIMARY KEY,

    name                   VARCHAR(100) NOT NULL,

    description            TEXT,

    avatar_url             TEXT,
    banner_url             TEXT,

    -- PUBLIC / PRIVATE
    visibility_type        VARCHAR(20)  NOT NULL DEFAULT 'PRIVATE',

    owner_id               BIGINT       NOT NULL,

    language_code          VARCHAR(10)           DEFAULT 'en',

    created_at             TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP,

    CONSTRAINT fk_chat_owner
        FOREIGN KEY (owner_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE chat_roles
(
    id BIGSERIAL PRIMARY KEY,

    chat_id BIGINT NOT NULL,

    role_name VARCHAR(50) NOT NULL,

    role_color VARCHAR(20),

    is_default BOOLEAN DEFAULT FALSE,

    can_delete_messages BOOLEAN DEFAULT FALSE,
    can_ban_users BOOLEAN DEFAULT FALSE,
    can_invite_users BOOLEAN DEFAULT FALSE,
    can_manage_roles BOOLEAN DEFAULT FALSE,
    can_manage_chat BOOLEAN DEFAULT FALSE,
    can_pin_messages BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_chat_roles_chat
        FOREIGN KEY (chat_id)
            REFERENCES chats(id)
            ON DELETE CASCADE
);

CREATE TABLE chat_members
(
    id        BIGSERIAL PRIMARY KEY,

    chat_id   BIGINT NOT NULL,
    user_id   BIGINT NOT NULL,
    role_id   BIGINT,

    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    is_muted BOOLEAN DEFAULT FALSE,
    banned_until TIMESTAMP,

    CONSTRAINT fk_chat_members_chat
        FOREIGN KEY (chat_id)
            REFERENCES chats (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_chat_members_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_chat_members_role
        FOREIGN KEY (role_id)
            REFERENCES chat_roles (id)
            ON DELETE SET NULL
);

CREATE TABLE messages
(
    id                 BIGSERIAL PRIMARY KEY,

    chat_id            BIGINT NOT NULL,
    sender_id          BIGINT NOT NULL,

    content            TEXT   NOT NULL,

    is_edited          BOOLEAN   DEFAULT FALSE,
    is_deleted         BOOLEAN   DEFAULT FALSE,

    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP,

    CONSTRAINT fk_messages_chat
        FOREIGN KEY (chat_id)
            REFERENCES chats (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_messages_sender
        FOREIGN KEY (sender_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);