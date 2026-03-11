package com.shank.Tech.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResultResponse {
    private Long playerId;
    private String playerName;
    private int score;
    private int questionsAttempted;
    private int correctAnswers;
    private int wrongAnswers;
    private int rank;
    private Long completionTimeSeconds;
}
