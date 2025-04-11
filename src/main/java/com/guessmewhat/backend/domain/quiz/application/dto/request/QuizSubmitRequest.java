package com.guessmewhat.backend.domain.quiz.application.dto.request;

import java.util.List;

public record QuizSubmitRequest(
        String nickname,  // 비회원용 닉네임 (회원이면 무시하거나 표시용)
        List<QuizAnswer> answers // 문제 번호와 사용자의 답
) {
    public record QuizAnswer(
            int number,
            String answer // "O" 또는 "X"
    ) {}
}
