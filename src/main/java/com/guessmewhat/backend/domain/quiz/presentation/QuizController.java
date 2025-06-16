package com.guessmewhat.backend.domain.quiz.presentation;

import com.guessmewhat.backend.domain.quiz.application.QuizService;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizSubmitRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizGenerateResponse;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizQuestionsResponse;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSetInfoResponse;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSubmitResultResponse;
import com.guessmewhat.backend.global.common.response.BaseResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponseData<QuizGenerateResponse> createQuiz(@RequestBody QuizCreateRequest request) {
        QuizGenerateResponse response = quizService.generateQuizSet(request);
        return BaseResponseData.created("quiz generated successfully", response);
    }

    @GetMapping("/{code}")
    public BaseResponseData<QuizSetInfoResponse> getQuizSetInfo(@PathVariable String code) {
        QuizSetInfoResponse response = quizService.getQuizSetInfo(code);
        return BaseResponseData.ok("quiz set retrieved successfully", response);
    }

    @GetMapping("/{code}/questions")
    public BaseResponseData<QuizQuestionsResponse> getQuizList(@PathVariable String code) {
        QuizQuestionsResponse response = quizService.getQuizList(code);
        return BaseResponseData.ok("quiz questions retrieved successfully", response);
    }

    @PostMapping("/{code}/submit")
    public BaseResponseData<QuizSubmitResultResponse> submitQuiz(
            @PathVariable String code,
            @RequestBody QuizSubmitRequest request
    ) {
        QuizSubmitResultResponse response = quizService.submitQuiz(code, request);
        return BaseResponseData.ok("quiz submitted successfully", response);
    }
}
