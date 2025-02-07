package com.minesweeper.repository;

import com.minesweeper.model.GameHistory;
import com.minesweeper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    List<GameHistory> findByUserOrderByPlayedAtDesc(User user);
}