package com.guessmewhat.backend.domain.quiz.application.dto.response;

import java.time.LocalDate;

public record QuizGenerateResponse(
        String code,
        LocalDate expirationDate
) {
}
