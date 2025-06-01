package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.GameRoom;
import com.nasipa.tictactoewebsocket.model.dto.GameMoveDto;
import com.nasipa.tictactoewebsocket.model.dto.GameRoomDto;
import com.nasipa.tictactoewebsocket.service.GameHistoryService;
import com.nasipa.tictactoewebsocket.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class GameRoomController {

    private final GameRoomService gameRoomService;
    private final GameHistoryService gameHistoryService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameRoomController(GameRoomService gameRoomService, GameHistoryService gameHistoryService, SimpMessagingTemplate messagingTemplate) {
        this.gameRoomService = gameRoomService;
        this.gameHistoryService = gameHistoryService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<GameRoomDto> createRoom() {
        GameRoom gameRoom = gameRoomService.createRoom();
        return ResponseEntity.ok(new GameRoomDto(gameRoom));
    }

    @GetMapping
    public ResponseEntity<List<GameRoomDto>> getAllRooms() {
        List<GameRoomDto> rooms = gameRoomService.getAllRooms().stream()
                .map(GameRoomDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<GameRoomDto> getRoom(@PathVariable String roomId) {
        GameRoom gameRoom = gameRoomService.getRoom(roomId);
        if (gameRoom != null) {
            return ResponseEntity.ok(new GameRoomDto(gameRoom));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{roomId}/join")
    public ResponseEntity<GameRoomDto> joinRoom(@PathVariable String roomId, @RequestParam String username) {
        GameRoom gameRoom = gameRoomService.joinRoom(roomId, username);
        if (gameRoom != null) {
            GameRoomDto roomDto = new GameRoomDto(gameRoom);
            messagingTemplate.convertAndSend("/topic/room/" + roomId, roomDto);
            return ResponseEntity.ok(roomDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{roomId}/leave")
    public ResponseEntity<?> leaveRoom(@PathVariable String roomId, @RequestParam String username) {
        GameRoom gameRoom = gameRoomService.leaveRoom(roomId, username);
        if (gameRoom != null) {
            if (gameRoom.isGameFinished()) {
                gameHistoryService.saveGameHistory(gameRoom);
            }
            GameRoomDto roomDto = new GameRoomDto(gameRoom);
            messagingTemplate.convertAndSend("/topic/room/" + roomId, roomDto);
            return ResponseEntity.ok(roomDto);
        }
        return ResponseEntity.ok().build(); // Room was deleted
    }

    @GetMapping("/stats/top-winners")
    public ResponseEntity<List<Map<String, Object>>> getTopWinners() {
        return ResponseEntity.ok(gameHistoryService.getTopWinners());
    }
} 