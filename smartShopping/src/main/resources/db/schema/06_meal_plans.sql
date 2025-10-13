CREATE TABLE meal_plans (
                            plan_id SERIAL PRIMARY KEY,
                            food_id INT REFERENCES foods(food_id) ON DELETE CASCADE,
                            group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                            meal_name VARCHAR(20) CHECK (meal_name IN ('breakfast','lunch','dinner','snack')) NOT NULL,
                            meal_date DATE NOT NULL,
                            meal_time TIME,
                            note TEXT,
                            created_by INT REFERENCES users(user_id) ON DELETE CASCADE,
                            created_at TIMESTAMP DEFAULT now(),
                            updated_at TIMESTAMP DEFAULT now()
);
