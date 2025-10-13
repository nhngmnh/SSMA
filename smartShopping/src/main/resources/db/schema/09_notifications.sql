CREATE TABLE notifications (
                               notification_id SERIAL PRIMARY KEY,
                               user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                               type VARCHAR(30) CHECK (type IN ('expiry_warning','task_assigned','group_invitation','general')) NOT NULL,
                               title VARCHAR(255) NOT NULL,
                               message TEXT NOT NULL,
                               related_id INT,
                               related_type VARCHAR(50),
                               is_read BOOLEAN DEFAULT FALSE,
                               is_sent BOOLEAN DEFAULT FALSE,
                               sent_at TIMESTAMP,
                               created_at TIMESTAMP DEFAULT now()
);
