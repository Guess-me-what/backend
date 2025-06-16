package com.guessmewhat.backend.domain.quiz.application;

import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizCreateRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.request.QuizSubmitRequest;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizGenerateResponse;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizQuestionsResponse;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSetInfoResponse;
import com.guessmewhat.backend.domain.quiz.application.dto.response.QuizSubmitResultResponse;
import com.guessmewhat.backend.domain.quiz.domain.Quiz;
import com.guessmewhat.backend.domain.quiz.domain.QuizSet;
import com.guessmewhat.backend.domain.quiz.domain.repository.QuizSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizSetRepository quizSetRepository;

    @Override
    @Transactional
    public QuizGenerateResponse generateQuizSet(QuizCreateRequest request) {
        String code = generateCode();

        List<Quiz> quizzes = request.questions().stream()
                .map(q -> new Quiz(q.question(), q.answer()))
                .toList();

        QuizSet quizSet = new QuizSet(code, request.nickname(), request.introduction(), quizzes);
        quizSetRepository.save(quizSet);

        return new QuizGenerateResponse(
                quizSet.getCode(),
                quizSet.getExpirationDate()
        );
    }

    @Override
    @Transactional
    public QuizSetInfoResponse getQuizSetInfo(String code) {
        QuizSet quizSet = quizSetRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 코드의 퀴즈가 존재하지 않습니다."));

        return new QuizSetInfoResponse(
                quizSet.getNickname(),
                quizSet.getIntroduction(),
                quizSet.getExpirationDate()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public QuizQuestionsResponse getQuizList(String code) {
        QuizSet quizSet = quizSetRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 코드의 퀴즈가 존재하지 않습니다."));

        List<QuizQuestionsResponse.QuizQuestion> questions = new ArrayList<>();
        int number = 1;
        for (Quiz quiz : quizSet.getQuizzes()) {
            questions.add(new QuizQuestionsResponse.QuizQuestion(number++, quiz.getQuestion()));
        }

        return new QuizQuestionsResponse(questions);
    }


    @Override
    @Transactional
    public QuizSubmitResultResponse submitQuiz(
            String code,
            QuizSubmitRequest request
    ) {
        QuizSet quizSet = quizSetRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("공유 코드를 찾을 수 없습니다."));

        List<Quiz> quizzes = quizSet.getQuizzes();

        // 정답 맵 생성: 문제 번호 → 실제 정답(boolean)
        Map<Integer, Boolean> correctAnswerMap = new HashMap<>();
        int number = 1;
        for (Quiz quiz : quizzes) {
            correctAnswerMap.put(number++, quiz.isAnswer());
        }

        int total = quizzes.size();
        int correctCount = 0;
        List<QuizSubmitResultResponse.WrongAnswer> wrongList = new ArrayList<>();

        for (int i = 0; i < request.answers().size(); i++) {
            QuizSubmitRequest.QuizAnswer submitted = request.answers().get(i);
            Boolean correct = correctAnswerMap.get(submitted.number());

            boolean userAnswer;
            if (submitted.answer().equalsIgnoreCase("O")) {
                userAnswer = true;
            } else if (submitted.answer().equalsIgnoreCase("X")) {
                userAnswer = false;
            } else {
                throw new IllegalArgumentException("유효하지 않은 정답 형식입니다. (O 또는 X)");
            }

            if (correct == userAnswer) {
                correctCount++;
            } else {
                String correctStr = correct ? "O" : "X";
                wrongList.add(new QuizSubmitResultResponse.WrongAnswer(submitted.number(), correctStr));
            }
        }

        int score = (int) ((correctCount / (double) total) * 100);

        return new QuizSubmitResultResponse(
                request.nickname(),
                score,
                wrongList
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
