package com.minesweeper.service;

import com.minesweeper.model.GameHistory;
import com.minesweeper.model.User;
import com.minesweeper.repository.GameHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameHistoryService {
    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    public GameHistory saveGame(GameHistory gameHistory) {
        gameHistory.setPlayedAt(LocalDateTime.now());
        return gameHistoryRepository.save(gameHistory);
    }

    public List<GameHistory> getGameHistoryForUser(User user) {
        return gameHistoryRepository.findByUserOrderByPlayedAtDesc(user);
    }
}