package com.guessmewhat.backend.quiz.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.guessmewhat.backend.domain.quiz.application.QuizService;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuestionCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizSubmitRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.response.*;
import com.guessmewhat.backend.domain.quiz.presentation.QuizController;
import com.guessmewhat.backend.global.common.response.BaseResponseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 퀴즈_생성_API() {
        // given
        QuizCreateRequest request = new QuizCreateRequest("닉네임", "소개",
                List.of(
                        new QuestionCreateRequest("문제1", true),
                        new QuestionCreateRequest("문제2", false),
                        new QuestionCreateRequest("문제3", true),
                        new QuestionCreateRequest("문제4", true),
                        new QuestionCreateRequest("문제5", false)
                ));
        QuizGenerateResponse mockResponse = new QuizGenerateResponse("ABC123", LocalDate.now().plusDays(7));
        when(quizService.generateQuizSet(any(QuizCreateRequest.class))).thenReturn(mockResponse);

        // when
        BaseResponseData<QuizGenerateResponse> response = quizController.createQuiz(request);

        // then
        assertEquals("quiz generated successfully", response.getMessage());
        assertEquals(mockResponse, response.getData());
        verify(quizService, times(1)).generateQuizSet(any());
    }

    @Test
    void 퀴즈셋_정보_조회_API() {
        // given
        String code = "ABCDE12";
        QuizSetInfoResponse mockResponse = new QuizSetInfoResponse("닉네임", "소개", LocalDate.now().plusDays(7));
        when(quizService.getQuizSetInfo(code)).thenReturn(mockResponse);

        // when
        BaseResponseData<QuizSetInfoResponse> response = quizController.getQuizSetInfo(code);

        // then
        assertEquals("quiz set retrieved successfully", response.getMessage());
        assertEquals(mockResponse, response.getData());
        verify(quizService, times(1)).getQuizSetInfo(code);
    }

    @Test
    void 퀴즈_문제_조회_API() {
        // given
        String code = "ABCDE12";
        QuizQuestionsResponse mockResponse = new QuizQuestionsResponse(
                List.of(
                        new QuizQuestionsResponse.QuizQuestion(1, "문제1"),
                        new QuizQuestionsResponse.QuizQuestion(2, "문제2")
                ));
        when(quizService.getQuizList(code)).thenReturn(mockResponse);

        // when
        BaseResponseData<QuizQuestionsResponse> response = quizController.getQuizList(code);

        // then
        assertEquals("quiz questions retrieved successfully", response.getMessage());
        assertEquals(mockResponse, response.getData());
        verify(quizService, times(1)).getQuizList(code);
    }

    @Test
    void 퀴즈_제출_API() {
        // given
        String code = "ABCDE12";
        QuizSubmitRequest request = new QuizSubmitRequest("guest",
                List.of(
                        new QuizSubmitRequest.QuizAnswer(1, "O"),
                        new QuizSubmitRequest.QuizAnswer(2, "X")
                ));
        QuizSubmitResultResponse mockResponse = new QuizSubmitResultResponse("guest", 50, List.of());
        when(quizService.submitQuiz(code, request)).thenReturn(mockResponse);

        // when
        BaseResponseData<QuizSubmitResultResponse> response = quizController.submitQuiz(code, request);

        // then
        assertEquals("quiz submitted successfully", response.getMessage());
        assertEquals(mockResponse, response.getData());
        verify(quizService, times(1)).submitQuiz(code, request);
    }
}
