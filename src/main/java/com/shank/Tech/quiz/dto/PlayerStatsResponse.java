package com.shank.Tech.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatsResponse {
    private Long playerId;
    private String playerName;
    private int questionsAttempted;
    private int correctAnswers;
    private int wrongAnswers;
    private int score;
    private Long completionTimeSeconds;
}
