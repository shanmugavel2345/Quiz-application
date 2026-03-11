package com.shank.Tech.quiz.controller;

import com.shank.Tech.quiz.dto.PlayerJoinRequest;
import com.shank.Tech.quiz.dto.PlayerJoinResponse;
import com.shank.Tech.quiz.dto.PlayerCompleteRequest;
import com.shank.Tech.quiz.dto.AnswerSubmitRequest;
import com.shank.Tech.quiz.dto.ScoreUpdateRequest;
import com.shank.Tech.quiz.entity.Player;
import com.shank.Tech.quiz.service.PlayerService;
import com.shank.Tech.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PlayerController {
    
    private final PlayerService playerService;
    private final QuizService quizService;
    
    @PostMapping("/join")
    public ResponseEntity<PlayerJoinResponse> joinPlayer(@RequestBody PlayerJoinRequest request) {
        Player player = playerService.joinPlayer(request.getName());
        PlayerJoinResponse response = new PlayerJoinResponse(
            player.getId(),
            player.getName(),
            "Successfully joined the quiz!"
        );
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/scoreboard")
    public ResponseEntity<List<Player>> getScoreboard() {
        List<Player> players = playerService.getPlayersSortedByScore();
        return ResponseEntity.ok(players);
    }
    
    @PostMapping("/score")
    public ResponseEntity<?> updateScore(@RequestBody ScoreUpdateRequest request) {
        if (!quizService.isQuizStarted()) {
            return ResponseEntity.status(409).body("Quiz has not started yet. Wait for admin to start.");
        }
        Player updatedPlayer = playerService.updateScore(request.getPlayerId(), request.getScoreIncrement());
        return ResponseEntity.ok(updatedPlayer);
    }

    @PostMapping("/answer")
    public ResponseEntity<?> submitAnswer(@RequestBody AnswerSubmitRequest request) {
        if (!quizService.isQuizStarted()) {
            return ResponseEntity.status(409).body("Quiz has not started yet. Wait for admin to start.");
        }
        Player updatedPlayer = playerService.submitAnswer(request.getPlayerId(), request.isCorrect());
        return ResponseEntity.ok(updatedPlayer);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completePlayerQuiz(@RequestBody PlayerCompleteRequest request) {
        if (!quizService.isQuizStarted()) {
            return ResponseEntity.status(409).body("Quiz has not started yet. Wait for admin to start.");
        }
        Player updatedPlayer = playerService.markPlayerCompleted(request.getPlayerId());
        return ResponseEntity.ok(updatedPlayer);
    }
}
