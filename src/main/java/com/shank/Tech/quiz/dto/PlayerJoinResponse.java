package com.shank.Tech.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerJoinResponse {
    private Long playerId;
    private String name;
    private String message;
}
