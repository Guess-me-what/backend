package com.guessmewhat.backend.domain.quiz.presentation;

import com.guessmewhat.backend.domain.quiz.application.QuizService;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizSubmitRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSetInfoResponse;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSubmitResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/generate")
    public ResponseEntity<String> createQuiz(@RequestBody QuizCreateRequest request) {
        String code = quizService.generateQuizSet(request);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/{code}")
    public ResponseEntity<QuizSetInfoResponse> getQuizSetInfo(@PathVariable String code) {
        QuizSetInfoResponse response = quizService.getQuizSetInfo(code);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{code}/submit")
    public ResponseEntity<QuizSubmitResultResponse> submitQuiz(
            @PathVariable String code,
            @RequestBody QuizSubmitRequest request
    ) {
        QuizSubmitResultResponse response = quizService.submitQuiz(code, request);
        return ResponseEntity.ok(response);
    }

}
