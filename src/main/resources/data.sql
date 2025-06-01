-- Sample game history data
INSERT INTO game_history (room_id, player_x, player_o, winner, is_draw, moves_count, game_date, completed_at)
VALUES 
    ('room1', 'player1', 'player2', 'player1', FALSE, 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('room2', 'player1', 'player3', 'player3', FALSE, 9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('room3', 'player2', 'player3', 'player2', FALSE, 7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('room4', 'player4', 'player1', 'player1', FALSE, 6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('room5', 'player3', 'player5', 'player3', FALSE, 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('room6', 'player2', 'player4', NULL, TRUE, 9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('room7', 'player5', 'player1', 'player1', FALSE, 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('room8', 'player3', 'player4', 'player3', FALSE, 7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('room9', 'player2', 'player5', 'player5', FALSE, 9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('room10', 'player1', 'player4', NULL, TRUE, 9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Insert sample users (password is 'password' encrypted with BCrypt)
INSERT INTO users (username, password, email, created_at, last_login)
VALUES ('user1', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'user1@example.com', '2023-01-01T10:00:00', NULL);

INSERT INTO users (username, password, email, created_at, last_login)
VALUES ('user2', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'user2@example.com', '2023-01-02T11:30:00', NULL);