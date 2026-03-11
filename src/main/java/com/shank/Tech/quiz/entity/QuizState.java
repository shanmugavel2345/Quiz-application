package com.shank.Tech.quiz.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_state")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizState {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private boolean started = false;

    @Column(nullable = false)
    private boolean ended = false;
}
