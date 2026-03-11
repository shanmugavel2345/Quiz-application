package com.shank.Tech.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String emoji;
    private List<String> options;
    private String correctAnswer;
}
