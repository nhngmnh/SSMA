CREATE TABLE shopping (
                          list_id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          group_id INT REFERENCES groups(group_id) ON DELETE CASCADE,
                          assigned_to INT REFERENCES users(user_id) ON DELETE SET NULL,
                          created_by INT REFERENCES users(user_id) ON DELETE CASCADE,
                          date DATE,
                          note TEXT,
                          status VARCHAR(20) CHECK (status IN ('pending','in_progress','completed','cancelled')) DEFAULT 'pending',
                          created_at TIMESTAMP DEFAULT now(),
                          updated_at TIMESTAMP DEFAULT now(),

    -- danh sách món cần mua
                          tasks JSONB DEFAULT '[]'::jsonb
);
