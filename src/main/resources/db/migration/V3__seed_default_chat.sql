-- DEFAULT CHAT
INSERT INTO chats (
    name,
    description,
    avatar_url,
    banner_url,
    visibility_type,
    owner_id,
    language_code,
    created_at,
    updated_at
)
SELECT
    'Catagram Global Chat',
    'Official Catagram space where all cats meow together 🐈',
    'https://cdn-icons-png.flaticon.com/512/616/616430.png',
    NULL,
    'PUBLIC',
    u.id,
    'en',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM users u
WHERE u.username = 'CatAdmin';

-- DEFAULT CHAT ROLE
INSERT INTO chat_roles (
    chat_id,
    role_name,
    role_color,
    is_default,
    can_delete_messages,
    can_ban_users,
    can_invite_users,
    can_manage_roles,
    can_manage_chat,
    can_pin_messages,
    created_at
)
SELECT
    c.id,
    'OWNER',
    '#ff9900',
    TRUE,
    TRUE,
    TRUE,
    TRUE,
    TRUE,
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP
FROM chats c
WHERE c.name = 'Catagram Global Chat';

-- ADD ADMIN AS CHAT MEMBER
INSERT INTO chat_members (
    chat_id,
    user_id,
    role_id,
    joined_at,
    muted_reason,
    banned_reason,
    muted_until,
    banned_until
)
SELECT
    c.id,
    u.id,
    r.id,
    CURRENT_TIMESTAMP,
    NULL,
    NULL,
    NULL,
    NULL
FROM chats c
         JOIN users u ON u.username = 'CatAdmin'
         JOIN chat_roles r ON r.chat_id = c.id AND r.role_name = 'OWNER'
WHERE c.name = 'Catagram Global Chat';


-- FIRST MESSAGE (MEOW)
INSERT INTO messages (
    chat_id,
    sender_id,
    content,
    is_edited,
    is_deleted,
    created_at,
    updated_at
)
SELECT
    c.id,
    u.id,
    'Meow 🐾',
    FALSE,
    FALSE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM chats c
         JOIN users u ON u.username = 'CatAdmin'
WHERE c.name = 'Catagram Global Chat';