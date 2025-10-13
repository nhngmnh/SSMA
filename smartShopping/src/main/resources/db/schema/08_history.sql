CREATE TABLE purchase_history (
                                  purchase_id SERIAL PRIMARY KEY,
                                  food_id INT REFERENCES foods(food_id) ON DELETE CASCADE,
                                  group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                                  user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                                  quantity NUMERIC(10,2) NOT NULL,
                                  purchase_date DATE NOT NULL,
                                  price NUMERIC(10,2),
                                  note TEXT,
                                  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE consumption_history (
                                     consumption_id SERIAL PRIMARY KEY,
                                     food_id INT REFERENCES foods(food_id) ON DELETE CASCADE,
                                     group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                                     user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                                     quantity NUMERIC(10,2) NOT NULL,
                                     consumption_date DATE NOT NULL,
                                     source VARCHAR(20) CHECK (source IN ('fridge','meal_plan','recipe','other')) DEFAULT 'other',
                                     note TEXT,
                                     created_at TIMESTAMP DEFAULT now()
);
