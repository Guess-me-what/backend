package com.guessmewhat.backend.domain.quiz.application.dto.response;

import java.time.LocalDate;

public record QuizSetInfoResponse(
        String nickname,
        String introduction,
        LocalDate expirationDate
) {}
