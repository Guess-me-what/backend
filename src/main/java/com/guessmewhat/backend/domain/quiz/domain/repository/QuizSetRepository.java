package com.guessmewhat.backend.domain.quiz.domain.repository;

import com.guessmewhat.backend.domain.quiz.domain.QuizSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizSetRepository extends JpaRepository<QuizSet, Long> {
    Optional<QuizSet> findByCode(String code);
}
