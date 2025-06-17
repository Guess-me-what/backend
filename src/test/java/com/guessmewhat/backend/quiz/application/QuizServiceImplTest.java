package com.guessmewhat.backend.quiz.application;

import com.guessmewhat.backend.domain.quiz.application.QuizServiceImpl;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuestionCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizSubmitRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.response.*;
import com.guessmewhat.backend.domain.quiz.domain.Quiz;
import com.guessmewhat.backend.domain.quiz.domain.QuizSet;
import com.guessmewhat.backend.domain.quiz.domain.repository.QuizSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceImplTest {

    private QuizSetRepository quizSetRepository;
    private QuizServiceImpl quizService;

    @BeforeEach
    void setUp() {
        quizSetRepository = mock(QuizSetRepository.class);
        quizService = new QuizServiceImpl(quizSetRepository);
    }

    @Test
    void 퀴즈_생성_성공() {
        // given
        QuizCreateRequest request = new QuizCreateRequest("닉네임", "소개",
                List.of(
                        new QuestionCreateRequest("문제1", true),
                        new QuestionCreateRequest("문제2", false),
                        new QuestionCreateRequest("문제3", true),
                        new QuestionCreateRequest("문제4", false),
                        new QuestionCreateRequest("문제5", true)
                )
        );

        // when
        QuizGenerateResponse response = quizService.generateQuizSet(request);

        // then
        ArgumentCaptor<QuizSet> captor = ArgumentCaptor.forClass(QuizSet.class);
        verify(quizSetRepository).save(captor.capture());
        QuizSet savedSet = captor.getValue();

        assertThat(response.code()).isNotNull();
        assertThat(savedSet.getNickname()).isEqualTo("닉네임");
        assertThat(savedSet.getQuizzes()).hasSize(5);
    }

    @Test
    void 퀴즈셋_조회_성공() {
        // given
        QuizSet mockSet = new QuizSet("ABC1234", "홍길동", "테스트 소개",
                List.of(new Quiz("문제", true))
        );
        when(quizSetRepository.findByCode("ABC1234")).thenReturn(Optional.of(mockSet));

        // when
        QuizSetInfoResponse response = quizService.getQuizSetInfo("ABC1234");

        // then
        assertThat(response.nickname()).isEqualTo("홍길동");
        assertThat(response.introduction()).isEqualTo("테스트 소개");
    }

    @Test
    void 퀴즈_문제_목록_조회() {
        // given
        Quiz quiz = new Quiz("문제1", true);
        QuizSet quizSet = new QuizSet("QZ123", "name", "intro", List.of(quiz));
        when(quizSetRepository.findByCode("QZ123")).thenReturn(Optional.of(quizSet));

        // when
        QuizQuestionsResponse result = quizService.getQuizList("QZ123");

        // then
        assertThat(result.questions()).hasSize(1);
        assertThat(result.questions().get(0).question()).isEqualTo("문제1");
    }

    @Test
    void 퀴즈_제출_정답_채점() {
        // given
        Quiz quiz1 = new Quiz("Q1", true);
        Quiz quiz2 = new Quiz("Q2", false);

        QuizSet quizSet = new QuizSet("TEST", "닉", "소개", List.of(quiz1, quiz2));
        when(quizSetRepository.findByCode("TEST")).thenReturn(Optional.of(quizSet));

        QuizSubmitRequest submitRequest = new QuizSubmitRequest("닉네임", List.of(
                new QuizSubmitRequest.QuizAnswer(1, "O"),
                new QuizSubmitRequest.QuizAnswer(2, "O") // 오답
        ));

        // when
        QuizSubmitResultResponse result = quizService.submitQuiz("TEST", submitRequest);

        // then
        assertThat(result.nickname()).isEqualTo("닉네임");
        assertThat(result.score()).isEqualTo(50);
        assertThat(result.wrongAnswers()).hasSize(1);
        assertThat(result.wrongAnswers().get(0).number()).isEqualTo(2);
        assertThat(result.wrongAnswers().get(0).correctAnswer()).isEqualTo("X");
    }

    @Test
    void 유효하지_않은_정답_제출시_예외() {
        // given
        QuizSet quizSet = new QuizSet("ABC1234", "nick", "intro",
                List.of(new Quiz("Q1", true)));
        when(quizSetRepository.findByCode("ABC1234")).thenReturn(Optional.of(quizSet));

        QuizSubmitRequest invalidAnswerRequest = new QuizSubmitRequest("닉", List.of(
                new QuizSubmitRequest.QuizAnswer(1, "INVALID")
        ));

        // when & then
        assertThatThrownBy(() -> quizService.submitQuiz("ABC1234", invalidAnswerRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효하지 않은 정답 형식");
    }
}
