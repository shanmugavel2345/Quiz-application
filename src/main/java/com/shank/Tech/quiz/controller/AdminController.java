package com.shank.Tech.quiz.controller;

import com.shank.Tech.quiz.dto.AdminStatsResponse;
import com.shank.Tech.quiz.dto.PlayerStatsResponse;
import com.shank.Tech.quiz.entity.Player;
import com.shank.Tech.quiz.service.PlayerService;
import com.shank.Tech.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/api/admin", "/admin"})
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final PlayerService playerService;
    private final QuizService quizService;

    @Value("${app.admin.secret}")
    private String adminSecret;

    private boolean isAdminAuthorized(String secretFromHeader) {
        return secretFromHeader != null && !secretFromHeader.isBlank() && secretFromHeader.equals(adminSecret);
    }

    @GetMapping("/players")
    public ResponseEntity<?> getAllPlayers(@RequestHeader(value = "X-Admin-Secret", required = false) String secret) {
        if (!isAdminAuthorized(secret)) {
            return ResponseEntity.status(403).body("Unauthorized admin access");
        }
        List<PlayerStatsResponse> players = playerService.getPlayerStats();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/scoreboard")
    public ResponseEntity<?> getScoreboard(@RequestHeader(value = "X-Admin-Secret", required = false) String secret) {
        if (!isAdminAuthorized(secret)) {
            return ResponseEntity.status(403).body("Unauthorized admin access");
        }
        List<Player> players = playerService.getPlayersSortedByScore();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestHeader(value = "X-Admin-Secret", required = false) String secret) {
        if (!isAdminAuthorized(secret)) {
            return ResponseEntity.status(403).body("Unauthorized admin access");
        }

        AdminStatsResponse response = new AdminStatsResponse(
            playerService.getTotalPlayers(),
            playerService.getCompletedPlayers()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/endQuiz")
    public ResponseEntity<?> endQuiz(@RequestHeader(value = "X-Admin-Secret", required = false) String secret) {
        if (!isAdminAuthorized(secret)) {
            return ResponseEntity.status(403).body("Unauthorized admin access");
        }
        quizService.endQuiz();
        playerService.finalizeActivePlayers();
        return ResponseEntity.ok("Quiz ended for all players");
    }
}