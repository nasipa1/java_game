package com.nasipa.tictactoewebsocket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DiceGameRoom {
    private String id;
    private String name;
    private String creator;
    private String player1;
    private String player2;
    private boolean gameStarted;
    private boolean gameFinished;
    private List<DiceRoll> rolls;
    private String winner;
    private String currentTurn;

    public DiceGameRoom() {
        this.id = UUID.randomUUID().toString();
        this.gameStarted = false;
        this.gameFinished = false;
        this.rolls = new ArrayList<>();
    }

    public DiceGameRoom(String name, String creator) {
        this();
        this.name = name;
        this.creator = creator;
        this.player1 = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public List<DiceRoll> getRolls() {
        return rolls;
    }

    public void setRolls(List<DiceRoll> rolls) {
        this.rolls = rolls;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public boolean isFull() {
        return player1 != null && player2 != null;
    }

    public boolean hasPlayer(String username) {
        return (player1 != null && player1.equals(username)) || 
               (player2 != null && player2.equals(username));
    }
} 