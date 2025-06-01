package com.nasipa.tictactoewebsocket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CoinToss {
    private String id;
    private String initiator;
    private String result; // "HEADS" or "TAILS"
    private boolean completed;
    private Map<String, String> userGuesses; // username -> "HEADS" or "TAILS"
    private List<String> winners;
    
    public CoinToss() {
        this.id = UUID.randomUUID().toString();
        this.completed = false;
        this.userGuesses = new HashMap<>();
        this.winners = new ArrayList<>();
    }
    
    public CoinToss(String initiator) {
        this();
        this.initiator = initiator;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getInitiator() {
        return initiator;
    }
    
    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public Map<String, String> getUserGuesses() {
        return userGuesses;
    }
    
    public void setUserGuesses(Map<String, String> userGuesses) {
        this.userGuesses = userGuesses;
    }
    
    public void addUserGuess(String username, String guess) {
        if (!completed && (guess.equals("HEADS") || guess.equals("TAILS"))) {
            userGuesses.put(username, guess);
        }
    }
    
    public List<String> getWinners() {
        return winners;
    }
    
    public void setWinners(List<String> winners) {
        this.winners = winners;
    }
    
    public String getWinner() {
        return winners.isEmpty() ? null : String.join(", ", winners);
    }
    
    public void toss() {
        Random random = new Random();
        this.result = random.nextBoolean() ? "HEADS" : "TAILS";
        this.completed = true;
        determineWinners();
    }
    
    private void determineWinners() {
        winners.clear();
        for (Map.Entry<String, String> entry : userGuesses.entrySet()) {
            if (entry.getValue().equals(result)) {
                winners.add(entry.getKey());
            }
        }
    }
} 