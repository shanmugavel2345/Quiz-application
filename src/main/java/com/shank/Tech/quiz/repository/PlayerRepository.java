package com.shank.Tech.quiz.repository;

import com.shank.Tech.quiz.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    
    List<Player> findAllByOrderByJoinedAtAsc();

    List<Player> findAllByOrderByScoreDesc();

    long countByCompletedTrue();

    List<Player> findAllByCompletedFalse();
}
