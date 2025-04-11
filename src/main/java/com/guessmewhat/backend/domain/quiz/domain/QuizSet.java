package com.guessmewhat.backend.domain.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String nickname;

    private String introduction;

    private LocalDate createdAt;

    private LocalDate expirationDate;

    @OneToMany(mappedBy = "quizSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes = new ArrayList<>();

    public QuizSet(String code, String nickname, String introduction, List<Quiz> quizzes) {
        this.code = code;
        this.nickname = nickname;
        this.introduction = introduction;
        this.createdAt = LocalDate.now();
        this.expirationDate = this.createdAt.plusDays(7);
        this.quizzes = quizzes;
        this.quizzes.forEach(q -> q.setQuizSet(this));
    }
}
