package com.nasipa.tictactoewebsocket.model.dto;

import com.nasipa.tictactoewebsocket.model.GameRoom;

public class GameRoomDto {
    private String id;
    private String playerX;
    private String playerO;
    private boolean isGameStarted;
    private boolean isGameFinished;
    private String currentTurn;
    private String winner;
    private String[][] board;

    public GameRoomDto() {
    }

    public GameRoomDto(GameRoom gameRoom) {
        this.id = gameRoom.getId();
        this.playerX = gameRoom.getPlayerX();
        this.playerO = gameRoom.getPlayerO();
        this.isGameStarted = gameRoom.isGameStarted();
        this.isGameFinished = gameRoom.isGameFinished();
        this.currentTurn = gameRoom.getCurrentTurn();
        this.winner = gameRoom.getWinner();
        this.board = gameRoom.getBoard();
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
    }

    public String getPlayerO() {
        return playerO;
    }

    public void setPlayerO(String playerO) {
        this.playerO = playerO;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }
} 