package com.guessmewhat.backend.domain.quiz.application;

import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizSubmitRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizGenerateResponse;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSetInfoResponse;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSubmitResultResponse;

public interface QuizService {
    QuizGenerateResponse generateQuizSet(QuizCreateRequest request);
    QuizSetInfoResponse getQuizSetInfo(String code);
    QuizSubmitResultResponse submitQuiz(String code, QuizSubmitRequest request);
}
