package com.nasipa.tictactoewebsocket.service;

import com.nasipa.tictactoewebsocket.model.CoinToss;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CoinTossService {
    private final Map<String, CoinToss> coinTosses = new ConcurrentHashMap<>();
    
    public CoinToss createCoinToss(String username) {
        CoinToss coinToss = new CoinToss(username);
        coinTosses.put(coinToss.getId(), coinToss);
        return coinToss;
    }
    
    public CoinToss getCoinToss(String id) {
        return coinTosses.get(id);
    }
    
    public Collection<CoinToss> getAllCoinTosses() {
        return coinTosses.values();
    }
    
    public CoinToss addUserGuess(String id, String username, String guess) {
        CoinToss coinToss = coinTosses.get(id);
        if (coinToss != null && !coinToss.isCompleted()) {
            coinToss.addUserGuess(username, guess);
        }
        return coinToss;
    }
    
    public CoinToss tossCoin(String id) {
        CoinToss coinToss = coinTosses.get(id);
        if (coinToss != null && !coinToss.isCompleted()) {
            coinToss.toss();
        }
        return coinToss;
    }
    
    public void removeCoinToss(String id) {
        coinTosses.remove(id);
    }
} 