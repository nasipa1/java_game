package com.nasipa.tictactoewebsocket.repository;

import com.nasipa.tictactoewebsocket.model.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    
    List<GameHistory> findByWinner(String winner);

    List<GameHistory> findByPlayerXOrPlayerO(String playerX, String playerO);
    
    List<GameHistory> findByGameDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<GameHistory> findByMovesCountLessThanEqual(Integer movesCount);
    
    @Query(value = "SELECT winner, COUNT(*) as wins FROM game_history WHERE winner IS NOT NULL AND is_draw = false GROUP BY winner ORDER BY wins DESC LIMIT 10", nativeQuery = true)
    List<Object[]> findTopWinners();
    
    @Query(value = "SELECT player_name, SUM(wins) as total_wins FROM (" +
                  "SELECT player_x as player_name, COUNT(*) as wins FROM game_history WHERE winner = player_x AND is_draw = false GROUP BY player_x " +
                  "UNION ALL " +
                  "SELECT player_o as player_name, COUNT(*) as wins FROM game_history WHERE winner = player_o AND is_draw = false GROUP BY player_o" +
                  ") as combined GROUP BY player_name ORDER BY total_wins DESC LIMIT 10", nativeQuery = true)
    List<Object[]> findTopWinnersAccurate();
} 