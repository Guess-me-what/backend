package com.guessmewhat.backend.domain.quiz.presentation;

import com.guessmewhat.backend.domain.quiz.application.QuizService;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSetInfoResponse;
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

}
