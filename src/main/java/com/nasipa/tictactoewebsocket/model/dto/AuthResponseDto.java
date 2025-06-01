package com.nasipa.tictactoewebsocket.model.dto;

public class AuthResponseDto {
    private String username;
    private String message;
    private boolean success;

    public AuthResponseDto() {
    }

    public AuthResponseDto(String username, String message, boolean success) {
        this.username = username;
        this.message = message;
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
} 