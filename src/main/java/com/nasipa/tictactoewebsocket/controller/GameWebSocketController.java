package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.GameRoom;
import com.nasipa.tictactoewebsocket.model.dto.GameMoveDto;
import com.nasipa.tictactoewebsocket.model.dto.GameRoomDto;
import com.nasipa.tictactoewebsocket.service.GameHistoryService;
import com.nasipa.tictactoewebsocket.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebSocketController {

    private final GameRoomService gameRoomService;
    private final GameHistoryService gameHistoryService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameWebSocketController(GameRoomService gameRoomService, GameHistoryService gameHistoryService, SimpMessagingTemplate messagingTemplate) {
        this.gameRoomService = gameRoomService;
        this.gameHistoryService = gameHistoryService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/move")
    public void handleMove(GameMoveDto moveDto) {
        GameRoom gameRoom = gameRoomService.makeMove(
                moveDto.getRoomId(),
                moveDto.getUsername(),
                moveDto.getRow(),
                moveDto.getCol()
        );
        
        if (gameRoom != null) {
            // Save game history if the game is finished
            if (gameRoom.isGameFinished()) {
                gameHistoryService.saveGameHistory(gameRoom);
            }
            
            GameRoomDto roomDto = new GameRoomDto(gameRoom);
            messagingTemplate.convertAndSend("/topic/room/" + moveDto.getRoomId(), roomDto);
        }
    }
} 