-- Insert sample users (password is 'password' encrypted with BCrypt)
INSERT INTO users (username, password, email, created_at, last_login)
VALUES ('user1', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'user1@example.com', '2023-01-01T10:00:00', NULL);

INSERT INTO users (username, password, email, created_at, last_login)
VALUES ('user2', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'user2@example.com', '2023-01-02T11:30:00', NULL);