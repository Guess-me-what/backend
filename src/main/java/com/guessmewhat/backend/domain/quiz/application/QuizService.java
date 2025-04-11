package com.guessmewhat.backend.domain.quiz.application;

import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSetInfoResponse;
import com.guessmewhat.backend.domain.quiz.domain.Quiz;
import com.guessmewhat.backend.domain.quiz.domain.QuizSet;
import com.guessmewhat.backend.domain.quiz.domain.repository.QuizSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizSetRepository quizSetRepository;

    public String generateQuizSet(QuizCreateRequest request) {
        String code = generateCode();

        List<Quiz> quizzes = request.questions().stream()
                .map(q -> new Quiz(q.question(), q.answer()))
                .toList();

        QuizSet quizSet = new QuizSet(code, request.nickname(), request.introduction(), quizzes);
        quizSetRepository.save(quizSet);
        return code;
    }

    public QuizSetInfoResponse getQuizSetInfo(String code) {
        QuizSet quizSet = quizSetRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 코드의 퀴즈가 존재하지 않습니다."));

        return new QuizSetInfoResponse(
                quizSet.getNickname(),
                quizSet.getIntroduction(),
                quizSet.getExpirationDate()
        );
    }


    private String generateCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}

