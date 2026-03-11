package com.shank.Tech.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizStatusResponse {
    private boolean started;
    private boolean ended;
}
