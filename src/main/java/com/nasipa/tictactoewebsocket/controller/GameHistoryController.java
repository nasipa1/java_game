package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.GameHistory;
import com.nasipa.tictactoewebsocket.service.GameHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
public class GameHistoryController {

    private final GameHistoryService gameHistoryService;

    @Autowired
    public GameHistoryController(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }

    @PostMapping
    public ResponseEntity<GameHistory> createGame(@RequestBody GameHistory gameHistory) {
        GameHistory savedGame = gameHistoryService.saveGame(gameHistory);
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GameHistory>> getAllGames() {
        List<GameHistory> games = gameHistoryService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameHistory> getGameById(@PathVariable Long id) {
        Optional<GameHistory> game = gameHistoryService.getGameById(id);
        return game.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/winner/{winner}")
    public ResponseEntity<List<GameHistory>> getGamesByWinner(@PathVariable String winner) {
        List<GameHistory> games = gameHistoryService.getGamesByWinner(winner);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/player/{playerName}")
    public ResponseEntity<List<GameHistory>> getGamesByPlayer(@PathVariable String playerName) {
        List<GameHistory> games = gameHistoryService.getGamesByPlayer(playerName);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<GameHistory>> getGamesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<GameHistory> games = gameHistoryService.getGamesByDateRange(startDate, endDate);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/max-moves/{maxMoves}")
    public ResponseEntity<List<GameHistory>> getGamesByMaxMoves(@PathVariable Integer maxMoves) {
        List<GameHistory> games = gameHistoryService.getGamesByMaxMoves(maxMoves);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameHistoryService.deleteGame(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
} 