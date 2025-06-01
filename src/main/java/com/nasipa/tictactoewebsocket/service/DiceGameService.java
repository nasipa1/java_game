package com.nasipa.tictactoewebsocket.service;

import com.nasipa.tictactoewebsocket.model.DiceGameRoom;
import com.nasipa.tictactoewebsocket.model.DiceRoll;
import com.nasipa.tictactoewebsocket.model.GameHistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class DiceGameService {
    
    private final Map<String, DiceGameRoom> gameRooms = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final GameHistoryService gameHistoryService;
    
    public DiceGameService(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }
    
    public DiceGameRoom createRoom(String name, String creator) {
        DiceGameRoom room = new DiceGameRoom(name, creator);
        gameRooms.put(room.getId(), room);
        return room;
    }
    
    public List<DiceGameRoom> getAllRooms() {
        return new ArrayList<>(gameRooms.values());
    }
    
    public DiceGameRoom getRoom(String roomId) {
        return gameRooms.get(roomId);
    }
    
    public DiceGameRoom joinRoom(String roomId, String player) {
        DiceGameRoom room = gameRooms.get(roomId);
        if (room != null && room.getPlayer2() == null && !player.equals(room.getPlayer1())) {
            room.setPlayer2(player);
        }
        return room;
    }
    
    public DiceRoll rollDice(String roomId, String player) {
        DiceGameRoom room = gameRooms.get(roomId);
        if (room == null || !room.hasPlayer(player) || room.isGameFinished()) {
            return null;
        }
        
        // Check if it's the player's turn
        if (room.getCurrentTurn() != null && !room.getCurrentTurn().equals(player)) {
            return null;
        }
        
        // Roll the dice (1-6)
        int roll = random.nextInt(6) + 1;
        DiceRoll diceRoll = new DiceRoll(player, roll);
        room.getRolls().add(diceRoll);
        
        // Set next player's turn
        if (player.equals(room.getPlayer1())) {
            room.setCurrentTurn(room.getPlayer2());
        } else {
            room.setCurrentTurn(room.getPlayer1());
        }
        
        // Check if game is finished (each player has rolled once)
        if (room.getRolls().size() >= 2) {
            determineWinner(room);
        } else if (room.getRolls().size() == 1) {
            // First roll, start the game
            room.setGameStarted(true);
        }
        
        return diceRoll;
    }
    
    private void determineWinner(DiceGameRoom room) {
        List<DiceRoll> rolls = room.getRolls();
        
        // Get the highest roll for each player
        Map<String, Integer> highestRolls = rolls.stream()
                .collect(Collectors.toMap(
                        DiceRoll::getPlayer,
                        DiceRoll::getValue,
                        Integer::max
                ));
        
        String player1 = room.getPlayer1();
        String player2 = room.getPlayer2();
        
        int player1Roll = highestRolls.getOrDefault(player1, 0);
        int player2Roll = highestRolls.getOrDefault(player2, 0);
        
        if (player1Roll > player2Roll) {
            room.setWinner(player1);
        } else if (player2Roll > player1Roll) {
            room.setWinner(player2);
        } else {
            // It's a tie, no winner
            room.setWinner("Tie");
        }
        
        room.setGameFinished(true);
        
        // Save game history if there's a winner (not a tie)
        if (!"Tie".equals(room.getWinner())) {
            saveGameHistory(room);
        }
    }
    
    private void saveGameHistory(DiceGameRoom room) {
        String winner = room.getWinner();
        String loser = winner.equals(room.getPlayer1()) ? room.getPlayer2() : room.getPlayer1();
        
        GameHistory gameHistory = new GameHistory(
                room.getId(),
                room.getPlayer1(),
                room.getPlayer2(),
                winner,
                false,  // not a draw
                room.getRolls().size()
        );
        
        gameHistoryService.saveGame(gameHistory);
    }
    
    public void leaveRoom(String roomId, String player) {
        DiceGameRoom room = gameRooms.get(roomId);
        if (room != null) {
            if (player.equals(room.getPlayer1())) {
                // If creator leaves and game hasn't started, remove the room
                if (!room.isGameStarted()) {
                    gameRooms.remove(roomId);
                } else if (!room.isGameFinished()) {
                    // If game started but not finished, other player wins
                    room.setWinner(room.getPlayer2());
                    room.setGameFinished(true);
                    saveGameHistory(room);
                }
            } else if (player.equals(room.getPlayer2())) {
                if (!room.isGameFinished()) {
                    // Player 2 leaves, player 1 wins
                    room.setWinner(room.getPlayer1());
                    room.setGameFinished(true);
                    saveGameHistory(room);
                }
            }
        }
    }
    
    public void startGame(String roomId) {
        DiceGameRoom room = gameRooms.get(roomId);
        if (room != null && room.isFull() && !room.isGameStarted()) {
            room.setGameStarted(true);
            room.setCurrentTurn(room.getPlayer1()); // Player 1 goes first
        }
    }
    
    public void removeRoom(String roomId) {
        gameRooms.remove(roomId);
    }
} 