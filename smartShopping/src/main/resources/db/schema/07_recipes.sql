CREATE TABLE recipes (
                         recipe_id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         food_id INT REFERENCES foods(food_id) ON DELETE CASCADE,
                         description TEXT,
                         html_content TEXT,
                         preparation_time INT,
                         cooking_time INT,
                         servings INT,
                         difficulty_level VARCHAR(10) CHECK (difficulty_level IN ('easy','medium','hard')),
                         created_by INT REFERENCES users(user_id) ON DELETE CASCADE,
                         created_at TIMESTAMP DEFAULT now(),
                         updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE recipe_ingredients (
                                    ingredient_id SERIAL PRIMARY KEY,
                                    recipe_id INT REFERENCES recipes(recipe_id) ON DELETE CASCADE,
                                    food_id INT REFERENCES foods(food_id) ON DELETE CASCADE,
                                    quantity NUMERIC(10,2) NOT NULL,
                                    note TEXT
);
