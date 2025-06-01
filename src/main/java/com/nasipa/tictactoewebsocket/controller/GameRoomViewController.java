package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.GameRoom;
import com.nasipa.tictactoewebsocket.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameRoomViewController {

    private final GameRoomService gameRoomService;

    @Autowired
    public GameRoomViewController(GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }

    @GetMapping("/rooms")
    public String listRooms(Model model) {
        model.addAttribute("rooms", gameRoomService.getAllRooms());
        return "game/rooms";
    }

    @GetMapping("/room/{roomId}")
    public String gameRoom(@PathVariable String roomId, Model model) {
        GameRoom gameRoom = gameRoomService.getRoom(roomId);
        if (gameRoom != null) {
            model.addAttribute("room", gameRoom);
            return "game/room";
        }
        return "redirect:/game/rooms";
    }
} 