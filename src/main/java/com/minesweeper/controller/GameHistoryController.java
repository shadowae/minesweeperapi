package com.minesweeper.controller;

import com.minesweeper.model.GameHistory;
import com.minesweeper.model.User;
import com.minesweeper.service.GameHistoryService;
import com.minesweeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
@RequestMapping("/api/games")
public class GameHistoryController {
    private static final Logger logger = LoggerFactory.getLogger(GameHistoryController.class);

    @Autowired
    private GameHistoryService gameHistoryService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> saveGame(@RequestBody GameHistory gameHistory,
                                      Authentication authentication) {
        try {
            logger.info("Attempting to save game for user: {}", authentication.getName());
            User user = userService.findByEmail(authentication.getName());
            if (user == null) {
                logger.error("User not found: {}", authentication.getName());
                return ResponseEntity.badRequest().body("User not found");
            }
            gameHistory.setUser(user);
            GameHistory saved = gameHistoryService.saveGame(gameHistory);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            logger.error("Error saving game", e);
            return ResponseEntity.badRequest().body("Error saving game: " + e.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getGameHistory(Authentication authentication) {
        try {
            logger.info("Fetching game history for user: {}", authentication.getName());
            User user = userService.findByEmail(authentication.getName());
            if (user == null) {
                logger.error("User not found: {}", authentication.getName());
                return ResponseEntity.badRequest().body("User not found");
            }
            return ResponseEntity.ok(gameHistoryService.getGameHistoryForUser(user));
        } catch (Exception e) {
            logger.error("Error fetching game history", e);
            return ResponseEntity.badRequest().body("Error fetching game history: " + e.getMessage());
        }
    }

    @GetMapping("/test-auth")
    public ResponseEntity<?> testAuth(Authentication authentication) {
        try {
            User user = userService.findByEmail(authentication.getName());
            return ResponseEntity.ok(Map.of(
                    "message", "Authentication successful",
                    "user", user.getEmail()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Auth error: " + e.getMessage());
        }
    }
}