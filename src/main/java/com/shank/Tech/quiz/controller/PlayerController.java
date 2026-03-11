package com.shank.Tech.quiz.controller;

import com.shank.Tech.quiz.dto.PlayerJoinRequest;
import com.shank.Tech.quiz.dto.PlayerJoinResponse;
import com.shank.Tech.quiz.dto.PlayerCompleteRequest;
import com.shank.Tech.quiz.dto.AnswerSubmitRequest;
import com.shank.Tech.quiz.dto.ScoreUpdateRequest;
import com.shank.Tech.quiz.entity.Player;
import com.shank.Tech.quiz.service.PlayerService;
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
    public ResponseEntity<Player> updateScore(@RequestBody ScoreUpdateRequest request) {
        Player updatedPlayer = playerService.updateScore(request.getPlayerId(), request.getScoreIncrement());
        return ResponseEntity.ok(updatedPlayer);
    }

    @PostMapping("/answer")
    public ResponseEntity<Player> submitAnswer(@RequestBody AnswerSubmitRequest request) {
        Player updatedPlayer = playerService.submitAnswer(request.getPlayerId(), request.isCorrect());
        return ResponseEntity.ok(updatedPlayer);
    }

    @PostMapping("/complete")
    public ResponseEntity<Player> completePlayerQuiz(@RequestBody PlayerCompleteRequest request) {
        Player updatedPlayer = playerService.markPlayerCompleted(request.getPlayerId());
        return ResponseEntity.ok(updatedPlayer);
    }
}
