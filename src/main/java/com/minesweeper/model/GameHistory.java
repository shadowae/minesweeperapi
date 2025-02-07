package com.minesweeper.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minesweeper.serialization.UserDTOSerializer;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "game_history")
public class GameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonSerialize(using = UserDTOSerializer.class)
    private User user;

    @Column(nullable = false)
    private LocalDateTime playedAt;

    @Column(nullable = false)
    private boolean won;

    @Column(nullable = false)
    private String difficultyLevel;

    @Column(nullable = false)
    private Integer gridSize;

    @Column(nullable = false)
    private Integer numberOfMines;
}