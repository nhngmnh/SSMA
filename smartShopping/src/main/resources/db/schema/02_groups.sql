CREATE TABLE groups (
                        group_id SERIAL PRIMARY KEY,
                        group_name VARCHAR(100) NOT NULL,
                        created_by INT REFERENCES users(user_id) ON DELETE CASCADE,
                        created_at TIMESTAMP DEFAULT now(),
                        updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE group_members (
                               member_id SERIAL PRIMARY KEY,
                               group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                               user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                               role VARCHAR(10) CHECK (role IN ('admin','member')) DEFAULT 'member',
                               joined_at TIMESTAMP DEFAULT now(),
                               UNIQUE (group_id, user_id)
);
