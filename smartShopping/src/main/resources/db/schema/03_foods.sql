CREATE TABLE food_categories (
                                 category_id SERIAL PRIMARY KEY,
                                 name VARCHAR(100) UNIQUE NOT NULL,
                                 description TEXT,
                                 created_at TIMESTAMP DEFAULT now(),
                                 updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE units (
                       unit_id SERIAL PRIMARY KEY,
                       unit_name VARCHAR(50) UNIQUE NOT NULL,
                       description VARCHAR(255),
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE foods (
                       food_id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       category_id INT REFERENCES food_categories(category_id),
                       unit_id INT REFERENCES units(unit_id),
                       group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                       image_url VARCHAR(500),
                       default_use_within INT DEFAULT 7,
                       created_by INT REFERENCES users(user_id) ON DELETE CASCADE,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP DEFAULT now(),
                       UNIQUE (name, group_id)
);
