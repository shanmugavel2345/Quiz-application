package com.shank.Tech.quiz.dto;

import com.shank.Tech.quiz.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizStartResponse {
    private int timerSeconds;
    private List<Question> questions;
}
