package com.guessmewhat.backend.domain.quiz.application.dto.response;

import java.util.List;

public record QuizQuestionsResponse(
        List<QuizQuestion> questions // 문제 리스트 (5개)
) {
    public record QuizQuestion(
            Integer number,
            String question
    ) {}
}
