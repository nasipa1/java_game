package com.nasipa.tictactoewebsocket.model.dto;

import com.nasipa.tictactoewebsocket.model.CoinToss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinTossDto {
    private String id;
    private String initiator;
    private String result;
    private boolean completed;
    private Map<String, String> userGuesses;
    private List<String> winners;
    
    public CoinTossDto() {
        this.userGuesses = new HashMap<>();
        this.winners = new ArrayList<>();
    }
    
    public CoinTossDto(CoinToss coinToss) {
        this.id = coinToss.getId();
        this.initiator = coinToss.getInitiator();
        this.result = coinToss.getResult();
        this.completed = coinToss.isCompleted();
        this.userGuesses = coinToss.getUserGuesses();
        this.winners = coinToss.getWinners();
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
    
    public List<String> getWinners() {
        return winners;
    }
    
    public void setWinners(List<String> winners) {
        this.winners = winners;
    }
    
    public String getWinner() {
        return winners.isEmpty() ? null : String.join(", ", winners);
    }
} 