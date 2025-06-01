package com.nasipa.tictactoewebsocket.model;

import java.util.UUID;
import java.time.LocalDateTime;

public class GameRoom {
    private String id;
    private String playerX;
    private String playerO;
    private boolean isGameStarted;
    private boolean isGameFinished;
    private String currentTurn;
    private String winner;
    private String[][] board;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GameRoom() {
        this.id = UUID.randomUUID().toString();
        this.isGameStarted = false;
        this.isGameFinished = false;
        this.board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerX() {
        return playerX;
    }

    public void setPlayerX(String playerX) {
        this.playerX = playerX;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPlayerO() {
        return playerO;
    }

    public void setPlayerO(String playerO) {
        this.playerO = playerO;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
        this.updatedAt = LocalDateTime.now();
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
        this.updatedAt = LocalDateTime.now();
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public boolean isFull() {
        return playerX != null && playerO != null;
    }
    
    public boolean hasPlayer(String username) {
        return (playerX != null && playerX.equals(username)) || 
               (playerO != null && playerO.equals(username));
    }
    
    public void makeMove(int row, int col, String player) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col].isEmpty()) {
            board[row][col] = player.equals(playerX) ? "X" : "O";
            currentTurn = player.equals(playerX) ? playerO : playerX;
            updatedAt = LocalDateTime.now();
            checkGameStatus();
        }
    }
    
    private void checkGameStatus() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                isGameFinished = true;
                winner = board[i][0].equals("X") ? playerX : playerO;
                return;
            }
        }
        
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (!board[0][i].isEmpty() && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])) {
                isGameFinished = true;
                winner = board[0][i].equals("X") ? playerX : playerO;
                return;
            }
        }
        
        // Check diagonals
        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            isGameFinished = true;
            winner = board[0][0].equals("X") ? playerX : playerO;
            return;
        }
        
        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            isGameFinished = true;
            winner = board[0][2].equals("X") ? playerX : playerO;
            return;
        }
        
        // Check for draw
        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    isDraw = false;
                    break;
                }
            }
            if (!isDraw) break;
        }
        
        if (isDraw) {
            isGameFinished = true;
            winner = "Draw";
        }
    }
} 