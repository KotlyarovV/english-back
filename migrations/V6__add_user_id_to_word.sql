ALTER TABLE IF EXISTS word ADD COLUMN user_id bigint,
ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users (id);