package com.nasipa.tictactoewebsocket.service;

import com.nasipa.tictactoewebsocket.model.GameRoom;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameRoomService {
    private final Map<String, GameRoom> gameRooms = new ConcurrentHashMap<>();

    public GameRoom createRoom() {
        GameRoom gameRoom = new GameRoom();
        gameRooms.put(gameRoom.getId(), gameRoom);
        return gameRoom;
    }

    public GameRoom getRoom(String roomId) {
        return gameRooms.get(roomId);
    }

    public Collection<GameRoom> getAllRooms() {
        return gameRooms.values();
    }

    public GameRoom joinRoom(String roomId, String username) {
        GameRoom gameRoom = gameRooms.get(roomId);
        if (gameRoom != null && !gameRoom.isFull() && !gameRoom.hasPlayer(username)) {
            if (gameRoom.getPlayerX() == null) {
                gameRoom.setPlayerX(username);
            } else {
                gameRoom.setPlayerO(username);
            }

            if (gameRoom.isFull() && !gameRoom.isGameStarted()) {
                gameRoom.setGameStarted(true);
                gameRoom.setCurrentTurn(gameRoom.getPlayerX());
            }
        }
        return gameRoom;
    }

    public GameRoom leaveRoom(String roomId, String username) {
        GameRoom gameRoom = gameRooms.get(roomId);
        if (gameRoom != null) {
            if (username.equals(gameRoom.getPlayerX())) {
                gameRoom.setPlayerX(null);
            } else if (username.equals(gameRoom.getPlayerO())) {
                gameRoom.setPlayerO(null);
            }

            if (gameRoom.getPlayerX() == null && gameRoom.getPlayerO() == null) {
                gameRooms.remove(roomId);
                return null;
            }

            if (gameRoom.isGameStarted() && !gameRoom.isGameFinished()) {
                gameRoom.setGameFinished(true);
                gameRoom.setWinner(username.equals(gameRoom.getPlayerX()) ? gameRoom.getPlayerO() : gameRoom.getPlayerX());
            }
        }
        return gameRoom;
    }

    public GameRoom makeMove(String roomId, String username, int row, int col) {
        GameRoom gameRoom = gameRooms.get(roomId);
        if (gameRoom != null && gameRoom.isGameStarted() && !gameRoom.isGameFinished() 
                && username.equals(gameRoom.getCurrentTurn())) {
            gameRoom.makeMove(row, col, username);
        }
        return gameRoom;
    }

    public void removeRoom(String roomId) {
        gameRooms.remove(roomId);
    }
} 