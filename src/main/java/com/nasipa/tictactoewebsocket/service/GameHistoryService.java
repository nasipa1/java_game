package com.nasipa.tictactoewebsocket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasipa.tictactoewebsocket.model.GameHistory;
import com.nasipa.tictactoewebsocket.model.GameRoom;
import com.nasipa.tictactoewebsocket.repository.GameHistoryRepository;

@Service
public class GameHistoryService {

    private final GameHistoryRepository gameHistoryRepository;

    @Autowired
    public GameHistoryService(GameHistoryRepository gameHistoryRepository) {
        this.gameHistoryRepository = gameHistoryRepository;
    }

    public GameHistory saveGameHistory(GameRoom gameRoom) {
        if (!gameRoom.isGameFinished()) {
            return null;
        }

        boolean isDraw = gameRoom.getWinner() != null && gameRoom.getWinner().equals("Draw");
        String winner = isDraw ? null : gameRoom.getWinner();

        int movesCount = 0;
        String[][] board = gameRoom.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null && !board[i][j].isEmpty()) {
                    movesCount++;
                }
            }
        }

        GameHistory gameHistory = new GameHistory(
                gameRoom.getId(),
                gameRoom.getPlayerX(),
                gameRoom.getPlayerO(),
                winner,
                isDraw,
                movesCount
        );

        return gameHistoryRepository.save(gameHistory);
    }

    public List<Map<String, Object>> getTopWinners() {
        List<Object[]> results = gameHistoryRepository.findTopWinnersAccurate();
        List<Map<String, Object>> topWinners = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> winner = new HashMap<>();
            winner.put("username", result[0]);
            winner.put("wins", result[1]);
            topWinners.add(winner);
        }

        return topWinners;
    }

    public GameHistory saveGame(GameHistory gameHistory) {
        return gameHistoryRepository.save(gameHistory);
    }

    public List<GameHistory> getAllGames() {
        return gameHistoryRepository.findAll();
    }

    public Optional<GameHistory> getGameById(Long id) {
        return gameHistoryRepository.findById(id);
    }

    public List<GameHistory> getGamesByWinner(String winner) {
        return gameHistoryRepository.findByWinner(winner);
    }

    public List<GameHistory> getGamesByPlayer(String playerName) {
        return gameHistoryRepository.findByPlayerXOrPlayerO(playerName, playerName);
    }

    public List<GameHistory> getGamesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return gameHistoryRepository.findByGameDateBetween(startDate, endDate);
    }

    public List<GameHistory> getGamesByMaxMoves(Integer maxMoves) {
        return gameHistoryRepository.findByMovesCountLessThanEqual(maxMoves);
    }

    public void deleteGame(Long id) {
        gameHistoryRepository.deleteById(id);
    }
} 