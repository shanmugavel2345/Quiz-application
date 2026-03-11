package com.shank.Tech.quiz.service;

import com.shank.Tech.quiz.dto.LeaderboardEntryResponse;
import com.shank.Tech.quiz.dto.PlayerResultResponse;
import com.shank.Tech.quiz.dto.PlayerStatsResponse;
import com.shank.Tech.quiz.entity.Player;
import com.shank.Tech.quiz.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {
    
    private final PlayerRepository playerRepository;
    
    @Transactional
    public Player joinPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        player.setScore(0);
        return playerRepository.save(player);
    }
    
    public List<Player> getAllPlayers() {
        return playerRepository.findAllByOrderByJoinedAtAsc();
    }
    
    public List<Player> getPlayersSortedByScore() {
        return playerRepository.findAllByOrderByScoreDesc();
    }

    public List<PlayerStatsResponse> getPlayerStats() {
        return playerRepository.findAllByOrderByJoinedAtAsc().stream()
            .map(player -> new PlayerStatsResponse(
                player.getId(),
                player.getName(),
                player.getQuestionsAttempted(),
                player.getCorrectAnswers(),
                player.getWrongAnswers(),
                player.getScore(),
                player.getCompletionTimeSeconds()
            ))
            .collect(Collectors.toList());
    }

    public long getTotalPlayers() {
        return playerRepository.count();
    }

    public long getCompletedPlayers() {
        return playerRepository.countByCompletedTrue();
    }
    
    @Transactional
    public Player updateScore(Long playerId, int scoreIncrement) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            player.setScore(player.getScore() + scoreIncrement);
            return playerRepository.save(player);
        }
        throw new RuntimeException("Player not found with id: " + playerId);
    }

    @Transactional
    public Player submitAnswer(Long playerId, boolean correct) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isEmpty()) {
            throw new RuntimeException("Player not found with id: " + playerId);
        }

        Player player = playerOpt.get();
        player.setQuestionsAttempted(player.getQuestionsAttempted() + 1);

        if (correct) {
            player.setCorrectAnswers(player.getCorrectAnswers() + 1);
            player.setScore(player.getScore() + 1);
        } else {
            player.setWrongAnswers(player.getWrongAnswers() + 1);
        }

        return playerRepository.save(player);
    }

    @Transactional
    public Player markPlayerCompleted(Long playerId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            player.setCompleted(true);
            if (player.getCompletionTimeSeconds() == null && player.getJoinedAt() != null) {
                long seconds = Math.max(0, Duration.between(player.getJoinedAt(), LocalDateTime.now()).getSeconds());
                player.setCompletionTimeSeconds(seconds);
            }
            return playerRepository.save(player);
        }
        throw new RuntimeException("Player not found with id: " + playerId);
    }

    @Transactional
    public void finalizeActivePlayers() {
        List<Player> activePlayers = playerRepository.findAllByCompletedFalse();
        LocalDateTime now = LocalDateTime.now();
        for (Player player : activePlayers) {
            player.setCompleted(true);
            if (player.getCompletionTimeSeconds() == null && player.getJoinedAt() != null) {
                long seconds = Math.max(0, Duration.between(player.getJoinedAt(), now).getSeconds());
                player.setCompletionTimeSeconds(seconds);
            }
        }
        playerRepository.saveAll(activePlayers);
    }

    public List<Player> getRankedPlayers() {
        return playerRepository.findAll().stream()
            .sorted(Comparator
                .comparingInt(Player::getScore).reversed()
                .thenComparing(p -> p.getCompletionTimeSeconds() == null ? Long.MAX_VALUE : p.getCompletionTimeSeconds())
                .thenComparing(Player::getJoinedAt)
            )
            .collect(Collectors.toList());
    }

    public PlayerResultResponse getPlayerResult(Long playerId) {
        List<Player> ranked = getRankedPlayers();
        for (int i = 0; i < ranked.size(); i++) {
            Player player = ranked.get(i);
            if (player.getId().equals(playerId)) {
                return new PlayerResultResponse(
                    player.getId(),
                    player.getName(),
                    player.getScore(),
                    player.getQuestionsAttempted(),
                    player.getCorrectAnswers(),
                    player.getWrongAnswers(),
                    i + 1,
                    player.getCompletionTimeSeconds()
                );
            }
        }
        throw new RuntimeException("Player not found with id: " + playerId);
    }

    public List<LeaderboardEntryResponse> getLeaderboard() {
        List<Player> ranked = getRankedPlayers();
        List<LeaderboardEntryResponse> leaderboard = new ArrayList<>();
        for (int i = 0; i < ranked.size(); i++) {
            Player player = ranked.get(i);
            leaderboard.add(new LeaderboardEntryResponse(
                i + 1,
                player.getId(),
                player.getName(),
                player.getScore()
            ));
        }
        return leaderboard;
    }
}
