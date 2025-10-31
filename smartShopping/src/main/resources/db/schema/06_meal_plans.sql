CREATE TABLE history (
                         history_id SERIAL PRIMARY KEY,
                         type VARCHAR(20) CHECK (type IN ('purchase','consumption')) NOT NULL,
                         food_id INT REFERENCES foods(food_id) ON DELETE CASCADE,
                         group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                         user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                         quantity NUMERIC(10,2) NOT NULL,
                         date DATE NOT NULL,
                         price NUMERIC(10,2),
                         source VARCHAR(20) DEFAULT 'other',
                         note TEXT,
                         created_at TIMESTAMP DEFAULT now()
);
