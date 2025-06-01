package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.CoinToss;
import com.nasipa.tictactoewebsocket.model.dto.CoinTossDto;
import com.nasipa.tictactoewebsocket.service.CoinTossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CoinTossWebSocketController {

    private final CoinTossService coinTossService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public CoinTossWebSocketController(CoinTossService coinTossService, SimpMessagingTemplate messagingTemplate) {
        this.coinTossService = coinTossService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/coin-toss")
    public void handleCoinToss(CoinTossDto coinTossDto) {
        CoinToss coinToss = coinTossService.getCoinToss(coinTossDto.getId());
        if (coinToss != null && !coinToss.isCompleted()) {
            coinToss.toss();
            CoinTossDto resultDto = new CoinTossDto(coinToss);
            messagingTemplate.convertAndSend("/topic/coin-toss/" + coinToss.getId(), resultDto);
        }
    }
    
    @MessageMapping("/coin-toss/guess")
    public void handleGuess(CoinTossDto coinTossDto) {
        if (coinTossDto.getId() != null && coinTossDto.getUserGuesses() != null && !coinTossDto.getUserGuesses().isEmpty()) {
            String username = coinTossDto.getUserGuesses().keySet().iterator().next();
            String guess = coinTossDto.getUserGuesses().get(username);
            
            CoinToss coinToss = coinTossService.addUserGuess(coinTossDto.getId(), username, guess);
            if (coinToss != null) {
                CoinTossDto resultDto = new CoinTossDto(coinToss);
                messagingTemplate.convertAndSend("/topic/coin-toss/" + coinToss.getId(), resultDto);
            }
        }
    }
} 