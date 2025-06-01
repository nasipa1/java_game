package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.DiceGameRoom;
import com.nasipa.tictactoewebsocket.model.DiceRoll;
import com.nasipa.tictactoewebsocket.service.DiceGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dice")
public class DiceGameController {

    private final DiceGameService diceGameService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public DiceGameController(DiceGameService diceGameService, SimpMessagingTemplate messagingTemplate) {
        this.diceGameService = diceGameService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public String diceHomePage() {
        return "dice/index";
    }

    @GetMapping("/rooms")
    public String diceRoomsPage(Model model) {
        model.addAttribute("rooms", diceGameService.getAllRooms());
        return "dice/rooms";
    }

    @GetMapping("/room/{roomId}")
    public String diceRoomPage(@PathVariable String roomId, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/auth/login";
        }

        DiceGameRoom room = diceGameService.getRoom(roomId);
        if (room == null) {
            return "redirect:/dice/rooms";
        }

        model.addAttribute("room", room);
        model.addAttribute("username", username);
        return "dice/room";
    }

    @PostMapping("/api/rooms")
    @ResponseBody
    public ResponseEntity<DiceGameRoom> createRoom(@RequestBody Map<String, String> payload, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        String roomName = payload.get("name");
        DiceGameRoom room = diceGameService.createRoom(roomName, username);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/api/rooms")
    @ResponseBody
    public ResponseEntity<List<DiceGameRoom>> getRooms() {
        return ResponseEntity.ok(diceGameService.getAllRooms());
    }

    @PostMapping("/api/rooms/{roomId}/join")
    @ResponseBody
    public ResponseEntity<DiceGameRoom> joinRoom(@PathVariable String roomId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        DiceGameRoom room = diceGameService.joinRoom(roomId, username);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }

        // Notify all subscribers about the room update
        messagingTemplate.convertAndSend("/topic/dice/room/" + roomId, room);
        return ResponseEntity.ok(room);
    }

    @MessageMapping("/dice/roll/{roomId}")
    @SendTo("/topic/dice/room/{roomId}")
    public DiceGameRoom rollDice(@DestinationVariable String roomId, Map<String, String> payload) {
        String username = payload.get("username");
        DiceRoll roll = diceGameService.rollDice(roomId, username);
        
        // Return the updated room
        return diceGameService.getRoom(roomId);
    }

    @MessageMapping("/dice/start/{roomId}")
    @SendTo("/topic/dice/room/{roomId}")
    public DiceGameRoom startGame(@DestinationVariable String roomId) {
        diceGameService.startGame(roomId);
        return diceGameService.getRoom(roomId);
    }

    @MessageMapping("/dice/leave/{roomId}")
    @SendTo("/topic/dice/room/{roomId}")
    public DiceGameRoom leaveRoom(@DestinationVariable String roomId, Map<String, String> payload) {
        String username = payload.get("username");
        diceGameService.leaveRoom(roomId, username);
        return diceGameService.getRoom(roomId);
    }
} 