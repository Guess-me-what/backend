package com.guessmewhat.backend.domain.quiz.application.dto.response;

import java.util.List;

public record QuizSubmitResultResponse(
        String nickname,
        int score, // 100, 80, 60 ...
        List<WrongAnswer> wrongAnswers // 틀린 문제 목록
) {
    public record WrongAnswer(
            int number,
            String correctAnswer
    ) {}
}
