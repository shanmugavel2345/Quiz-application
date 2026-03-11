package com.shank.Tech.quiz.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private int score = 0;

    @Column(nullable = false)
    private int questionsAttempted = 0;

    @Column(nullable = false)
    private int correctAnswers = 0;

    @Column(nullable = false)
    private int wrongAnswers = 0;

    @Column(nullable = false)
    private boolean completed = false;

    @Column
    private Long completionTimeSeconds;
    
    @Column(nullable = false)
    private LocalDateTime joinedAt;
    
    @PrePersist
    protected void onCreate() {
        joinedAt = LocalDateTime.now();
        questionsAttempted = 0;
        correctAnswers = 0;
        wrongAnswers = 0;
        score = 0;
        completed = false;
        completionTimeSeconds = null;
    }
}
