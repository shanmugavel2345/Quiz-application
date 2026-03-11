package com.shank.Tech.quiz.controller;

import com.shank.Tech.quiz.dto.QuizStartResponse;
import com.shank.Tech.quiz.model.Question;
import com.shank.Tech.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuizQuestionController {

    private final QuizService quizService;

    @GetMapping("/start")
    public ResponseEntity<QuizStartResponse> startQuiz() {
        List<Question> questions = quizService.getRandomQuestions(30);
        return ResponseEntity.ok(new QuizStartResponse(15, questions));
    }
}
