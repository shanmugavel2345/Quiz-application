package com.shank.Tech.quiz.repository;

import com.shank.Tech.quiz.entity.QuizState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizStateRepository extends JpaRepository<QuizState, Long> {
}
