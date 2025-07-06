CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    last_login_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(20)
    );

CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    user_id INTEGER REFERENCES users(id),
    like_count INTEGER DEFAULT 0, -- 기본값 0 설정
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS comments (
    id SERIAL PRIMARY KEY,
    post_id INTEGER REFERENCES posts(id),
    user_id INTEGER REFERENCES users(id),
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS likes (
    id SERIAL PRIMARY KEY,
    post_id INTEGER REFERENCES posts(id),
    user_id INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(post_id, user_id)
    );

CREATE TABLE IF NOT EXISTS announcements (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS notifications (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE FUNCTION increment_like_count()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE posts
    SET like_count = like_count + 1
    WHERE id = NEW.post_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_increment_like_count
AFTER INSERT ON likes
FOR EACH ROW
EXECUTE FUNCTION increment_like_count();

CREATE OR REPLACE FUNCTION decrement_like_count()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE posts
    SET like_count = GREATEST(like_count - 1, 0)
    WHERE id = OLD.post_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_decrement_like_count
    AFTER DELETE ON likes
    FOR EACH ROW
    EXECUTE FUNCTION decrement_like_count();

