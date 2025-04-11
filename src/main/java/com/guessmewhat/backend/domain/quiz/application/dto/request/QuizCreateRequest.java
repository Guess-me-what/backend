package com.guessmewhat.backend.domain.quiz.application.dto.request;

import java.util.List;

public record QuizCreateRequest(
        String nickname, // 유저 닉네임
        String introduction, // 한 줄 소개
        List<QuestionCreateRequest> questions // 문제 리스트 (5개)
) {}
