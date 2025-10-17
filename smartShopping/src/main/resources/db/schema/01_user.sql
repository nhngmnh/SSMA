CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       username VARCHAR(50) UNIQUE,
                       gender VARCHAR(10) CHECK (gender IN ('male','female','other')),
                       date_of_birth DATE,
                       language VARCHAR(10) DEFAULT 'vi',
                       timezone VARCHAR(50) DEFAULT 'Asia/Ho_Chi_Minh',
                       device_id VARCHAR(255),
                       profile_image_url VARCHAR(500),
                       is_verified BOOLEAN DEFAULT FALSE,
                       is_active BOOLEAN DEFAULT TRUE,
                       is_admin BOOLEAN DEFAULT FALSE,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP DEFAULT now(),
                       last_login TIMESTAMP,

    -- verification
                       verification_code VARCHAR(10),
                       verification_token VARCHAR(500),
                       verification_expires_at TIMESTAMP,
                       verification_used BOOLEAN DEFAULT FALSE,

    -- refresh token
                       refresh_token VARCHAR(500),
                       refresh_expires_at TIMESTAMP,
                       refresh_revoked BOOLEAN DEFAULT FALSE
);
