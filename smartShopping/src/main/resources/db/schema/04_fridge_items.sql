CREATE TABLE fridge_items (
                              item_id SERIAL PRIMARY KEY,
                              food_id INT REFERENCES foods(food_id) ON DELETE CASCADE,
                              group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                              quantity NUMERIC(10,2) NOT NULL,
                              use_within INT NOT NULL,
                              expiry_date DATE NOT NULL,
                              storage_location VARCHAR(100),
                              note TEXT,
                              is_consumed BOOLEAN DEFAULT FALSE,
                              consumed_at TIMESTAMP,
                              created_by INT REFERENCES users(user_id) ON DELETE CASCADE,
                              created_at TIMESTAMP DEFAULT now(),
                              updated_at TIMESTAMP DEFAULT now()
);
