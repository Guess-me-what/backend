package com.guessmewhat.backend.domain.quiz.application.dto.response;

public record QuestionCreateRequest(
        String question, // 문제 내용
        boolean answer // 정답 (true = O, false = X)
) {}
