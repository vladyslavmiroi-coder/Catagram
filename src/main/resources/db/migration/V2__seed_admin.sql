INSERT INTO users (
    username,
    phone_number,
    password,
    language_code
)
VALUES (
           'CatAdmin',
           '000-MEOW',
           '$2a$10$QkccCpMTsNW0N0XIlyDeROV/HC.Rv55WFujLP9URoFxN48Sho.03K',
           'uk'
       )
ON CONFLICT (username) DO NOTHING;

-- ADMIN PROFILE

INSERT INTO user_profiles (
    user_id,
    email,
    bio,
    country,
    city,
    created_at
)
SELECT
    u.id,
    'bigboss@catagram.com',
    'Professional keyboard sleeper and laser pointer hunter',
    'Cat Empire',
    'Mrrrp City',
    CURRENT_TIMESTAMP
FROM users u
WHERE u.username = 'CatAdmin'
ON CONFLICT (user_id) DO NOTHING;

-- ADMIN ROLE LINK

INSERT INTO user_roles (
    user_id,
    role_id
)
SELECT
    u.id,
    r.id
FROM users u
         CROSS JOIN roles r
WHERE u.username = 'CatAdmin'
  AND r.role_name = 'ADMIN'
ON CONFLICT (user_id, role_id) DO NOTHING;