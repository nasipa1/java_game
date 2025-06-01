-- Create game_history table if it doesn't exist
CREATE TABLE IF NOT EXISTS game_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id VARCHAR(255) NOT NULL,
    player_x VARCHAR(255) NOT NULL,
    player_o VARCHAR(255) NOT NULL,
    winner VARCHAR(255),
    is_draw BOOLEAN DEFAULT FALSE,
    moves_count INT,
    game_date TIMESTAMP NOT NULL,
    completed_at TIMESTAMP NOT NULL
);

-- Create index for faster queries
CREATE INDEX IF NOT EXISTS idx_game_history_winner ON game_history(winner);
CREATE INDEX IF NOT EXISTS idx_game_history_players ON game_history(player_x, player_o);
CREATE INDEX IF NOT EXISTS idx_game_history_date ON game_history(game_date);

-- Create users table if it doesn't exist
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    created_at TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);

-- Create index for faster user queries
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username); 