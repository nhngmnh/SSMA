CREATE TABLE groups (
                        group_id SERIAL PRIMARY KEY,
                        group_name VARCHAR(100) NOT NULL,
                        created_by INT REFERENCES users(user_id) ON DELETE CASCADE,
                        created_at TIMESTAMP DEFAULT now(),
                        updated_at TIMESTAMP DEFAULT now(),

    -- danh sách member dưới dạng JSON
                        members JSONB DEFAULT '[]'::jsonb
);
