package com.nasipa.tictactoewebsocket.model;

public class DiceRoll {
    private String player;
    private int value;
    private long timestamp;

    public DiceRoll() {
    }

    public DiceRoll(String player, int value) {
        this.player = player;
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
} 