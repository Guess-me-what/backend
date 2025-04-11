package com.guessmewhat.backend.domain.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private boolean answer; // true = O, false = X

    @ManyToOne(fetch = FetchType.LAZY)
    private QuizSet quizSet;

    public Quiz(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public void setQuizSet(QuizSet quizSet) {
        this.quizSet = quizSet;
    }
}
