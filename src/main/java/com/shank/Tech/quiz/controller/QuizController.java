package com.shank.Tech.quiz.controller;

import com.shank.Tech.quiz.dto.QuizStatusResponse;
import com.shank.Tech.quiz.dto.LeaderboardEntryResponse;
import com.shank.Tech.quiz.dto.PlayerResultResponse;
import com.shank.Tech.quiz.service.PlayerService;
import com.shank.Tech.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/quiz", "/quiz"})
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuizController {
    
    private final QuizService quizService;
    private final PlayerService playerService;

    @Value("${app.admin.secret}")
    private String adminSecret;

    private boolean isAdminAuthorized(String secretFromHeader) {
        return secretFromHeader != null && !secretFromHeader.isBlank() && secretFromHeader.equals(adminSecret);
    }
    
    @PostMapping("/start")
    public ResponseEntity<String> startQuiz(@RequestHeader(value = "X-Admin-Secret", required = false) String secret) {
        if (!isAdminAuthorized(secret)) {
            return ResponseEntity.status(403).body("Unauthorized admin access");
        }
        quizService.startQuiz();
        return ResponseEntity.ok("Quiz started successfully!");
    }

    @PostMapping("/end")
    public ResponseEntity<String> endQuiz(@RequestHeader(value = "X-Admin-Secret", required = false) String secret) {
        if (!isAdminAuthorized(secret)) {
            return ResponseEntity.status(403).body("Unauthorized admin access");
        }
        quizService.endQuiz();
        playerService.finalizeActivePlayers();
        return ResponseEntity.ok("Quiz ended successfully!");
    }
    
    @GetMapping("/status")
    public ResponseEntity<QuizStatusResponse> getQuizStatus() {
        boolean started = quizService.isQuizStarted();
        boolean ended = quizService.isQuizEnded();
        return ResponseEntity.ok(new QuizStatusResponse(started, ended));
    }
    
    @PostMapping("/reset")
    public ResponseEntity<String> resetQuiz(@RequestHeader(value = "X-Admin-Secret", required = false) String secret) {
        if (!isAdminAuthorized(secret)) {
            return ResponseEntity.status(403).body("Unauthorized admin access");
        }
        quizService.resetQuiz();
        return ResponseEntity.ok("Quiz reset successfully!");
    }

    @GetMapping("/result/{playerId}")
    public ResponseEntity<PlayerResultResponse> getPlayerResult(@PathVariable Long playerId) {
        try {
            return ResponseEntity.ok(playerService.getPlayerResult(playerId));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntryResponse>> getLeaderboard() {
        return ResponseEntity.ok(playerService.getLeaderboard());
    }
}
