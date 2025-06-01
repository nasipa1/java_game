package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.CoinToss;
import com.nasipa.tictactoewebsocket.model.dto.CoinTossDto;
import com.nasipa.tictactoewebsocket.service.CoinTossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coin-toss")
public class CoinTossController {

    private final CoinTossService coinTossService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public CoinTossController(CoinTossService coinTossService, SimpMessagingTemplate messagingTemplate) {
        this.coinTossService = coinTossService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public ResponseEntity<List<CoinTossDto>> getAllCoinTosses() {
        List<CoinTossDto> coinTosses = coinTossService.getAllCoinTosses().stream()
                .filter(ct -> !ct.isCompleted())
                .map(CoinTossDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(coinTosses);
    }

    @PostMapping
    public ResponseEntity<CoinTossDto> createCoinToss(@RequestParam String username) {
        CoinToss coinToss = coinTossService.createCoinToss(username);
        return ResponseEntity.ok(new CoinTossDto(coinToss));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoinTossDto> getCoinToss(@PathVariable String id) {
        CoinToss coinToss = coinTossService.getCoinToss(id);
        if (coinToss != null) {
            return ResponseEntity.ok(new CoinTossDto(coinToss));
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/guess")
    public ResponseEntity<CoinTossDto> makeGuess(@PathVariable String id, 
                                               @RequestParam String username, 
                                               @RequestParam String guess) {
        CoinToss coinToss = coinTossService.addUserGuess(id, username, guess);
        if (coinToss != null) {
            CoinTossDto coinTossDto = new CoinTossDto(coinToss);
            messagingTemplate.convertAndSend("/topic/coin-toss/" + id, coinTossDto);
            return ResponseEntity.ok(coinTossDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/toss")
    public ResponseEntity<CoinTossDto> tossCoin(@PathVariable String id) {
        CoinToss coinToss = coinTossService.tossCoin(id);
        if (coinToss != null) {
            CoinTossDto coinTossDto = new CoinTossDto(coinToss);
            messagingTemplate.convertAndSend("/topic/coin-toss/" + id, coinTossDto);
            return ResponseEntity.ok(coinTossDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCoinToss(@PathVariable String id) {
        coinTossService.removeCoinToss(id);
        return ResponseEntity.ok().build();
    }
} 