CREATE TABLE shopping_lists (
                                list_id SERIAL PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                                assigned_to INT REFERENCES users(user_id) ON DELETE SET NULL,
                                created_by INT REFERENCES users(user_id) ON DELETE CASCADE,
                                date DATE,
                                note TEXT,
                                status VARCHAR(20) CHECK (status IN ('pending','in_progress','completed','cancelled')) DEFAULT 'pending',
                                created_at TIMESTAMP DEFAULT now(),
                                updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE shopping_tasks (
                                task_id SERIAL PRIMARY KEY,
                                list_id INT REFERENCES shopping_lists(list_id) ON DELETE CASCADE,
                                food_id INT REFERENCES foods(food_id) ON DELETE CASCADE,
                                quantity NUMERIC(10,2) NOT NULL,
                                is_purchased BOOLEAN DEFAULT FALSE,
                                purchased_at TIMESTAMP,
                                note TEXT,
                                created_at TIMESTAMP DEFAULT now(),
                                updated_at TIMESTAMP DEFAULT now(),
                                UNIQUE (list_id, food_id)
);
