package otus.amogilevskiy.spring.service.quiz;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.service.question.QuestionService;
import otus.amogilevskiy.spring.service.user.UserService;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionService questionService;

    private final UserService userService;

    private final QuizResultService quizResultService;

    public QuizServiceImpl(QuestionService questionService,
                           UserService userService,
                           QuizResultService quizResultService) {
        this.questionService = questionService;
        this.userService = userService;
        this.quizResultService = quizResultService;
    }

    @Override
    public void startQuiz() {
        var user = userService.showUserForm();
        var results = questionService.showQuestionsForm();
        quizResultService.showResults(user, results);
    }

}
