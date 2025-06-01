package com.nasipa.tictactoewebsocket.model.dto;

public class GameMoveDto {
    private String roomId;
    private String username;
    private int row;
    private int col;

    public GameMoveDto() {
    }

    public GameMoveDto(String roomId, String username, int row, int col) {
        this.roomId = roomId;
        this.username = username;
        this.row = row;
        this.col = col;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
} 