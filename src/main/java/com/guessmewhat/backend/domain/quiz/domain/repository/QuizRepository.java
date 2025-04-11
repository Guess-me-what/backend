package com.guessmewhat.backend.domain.quiz.domain.repository;

import com.guessmewhat.backend.domain.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
//    List<Quiz> findByQuizSetIdOrderByNumberAsc(Long quizSetId);
}
