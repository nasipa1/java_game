package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.CoinToss;
import com.nasipa.tictactoewebsocket.service.CoinTossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class CoinTossViewController {

    private final CoinTossService coinTossService;
    
    @Autowired
    public CoinTossViewController(CoinTossService coinTossService) {
        this.coinTossService = coinTossService;
    }

    @GetMapping("/coin-toss")
    public String getCoinTossPage() {
        return "game/coin-toss";
    }
    
    @GetMapping("/coin-toss/rooms")
    public String getCoinTossRoomsPage() {
        return "game/coin-toss-rooms";
    }
    
    @GetMapping("/coin-toss/{id}")
    public String getCoinTossRoomPage(@PathVariable String id, Model model) {
        CoinToss coinToss = coinTossService.getCoinToss(id);
        if (coinToss == null) {
            return "redirect:/game/coin-toss/rooms";
        }
        model.addAttribute("coinToss", coinToss);
        return "game/coin-toss-room";
    }
} 