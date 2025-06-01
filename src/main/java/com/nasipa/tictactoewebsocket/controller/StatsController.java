package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.service.GameHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stats")
public class StatsController {

    private final GameHistoryService gameHistoryService;

    @Autowired
    public StatsController(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }

    @GetMapping
    public String statsPage(Model model) {
        model.addAttribute("topWinners", gameHistoryService.getTopWinners());
        return "stats";
    }

    @GetMapping("/api/top-winners")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getTopWinners() {
        return ResponseEntity.ok(gameHistoryService.getTopWinners());
    }
} 