package com.nasipa.tictactoewebsocket.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_history")
public class GameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "player_x", nullable = false)
    private String playerX;

    @Column(name = "player_o", nullable = false)
    private String playerO;

    @Column(name = "winner")
    private String winner;

    @Column(name = "is_draw")
    private boolean isDraw;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;
    
    @Column(name = "game_date", nullable = false)
    private LocalDateTime gameDate;
    
    @Column(name = "moves_count")
    private Integer movesCount;

    public GameHistory() {
        this.completedAt = LocalDateTime.now();
        this.gameDate = LocalDateTime.now();
    }

    public GameHistory(String roomId, String playerX, String playerO, String winner, boolean isDraw) {
        this.roomId = roomId;
        this.playerX = playerX;
        this.playerO = playerO;
        this.winner = winner;
        this.isDraw = isDraw;
        this.completedAt = LocalDateTime.now();
        this.gameDate = LocalDateTime.now();
    }

    public GameHistory(String roomId, String playerX, String playerO, String winner, boolean isDraw, Integer movesCount) {
        this(roomId, playerX, playerO, winner, isDraw);
        this.movesCount = movesCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPlayerX() {
        return playerX;
    }

    public void setPlayerX(String playerX) {
        this.playerX = playerX;
    }

    public String getPlayerO() {
        return playerO;
    }

    public void setPlayerO(String playerO) {
        this.playerO = playerO;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }

    public Integer getMovesCount() {
        return movesCount;
    }

    public void setMovesCount(Integer movesCount) {
        this.movesCount = movesCount;
    }
} 