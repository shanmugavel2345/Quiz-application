package com.shank.Tech.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardEntryResponse {
    private int rank;
    private Long playerId;
    private String playerName;
    private int score;
}
