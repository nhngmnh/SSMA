CREATE TABLE foods (
                       food_id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       category_name VARCHAR(100),
                       unit_name VARCHAR(50),
                       group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                       image_url VARCHAR(500),
                       description TEXT,
                       default_use_within INT DEFAULT 7,
                       created_by INT REFERENCES users(user_id) ON DELETE CASCADE,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP DEFAULT now(),
                       UNIQUE (name, group_id)
);
