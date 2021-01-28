package otus.amogilevskiy.spring.service.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.service.auth.AuthService;
import otus.amogilevskiy.spring.service.question.QuestionService;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final AuthService authService;
    private final QuestionService questionService;
    private final QuizResultService quizResultService;

    @Override
    public void startQuiz() {
        authService.getCurrentUser().ifPresent(user -> {
            var results = questionService.showQuestionsForm();
            quizResultService.showResults(user, results);
        });
    }

}
