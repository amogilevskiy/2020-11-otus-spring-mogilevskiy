package otus.amogilevskiy.spring.service;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dao.QuestionDao;
import otus.amogilevskiy.spring.domain.Question;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionDao questionDao;

    private final QuestionStringFormatter questionStringFormatter;

    private final IOService ioService;

    public QuizServiceImpl(QuestionDao questionDao,
                           QuestionStringFormatter questionStringFormatter,
                           IOService ioService
    ) {
        this.questionDao = questionDao;
        this.questionStringFormatter = questionStringFormatter;
        this.ioService = ioService;
    }

    @Override
    public void startQuiz() {
        var questions = questionDao.findAll();
        if (questions.size() == 0) {
            showQuizError("Questions not found.");
        } else {
            showQuestions(questions);
        }
    }

    private void showQuizError(String message) {
        ioService.out(message);
    }

    private void showQuestions(List<Question> questions) {
        var index = 1;
        for (var question : questions) {
            ioService.out(questionStringFormatter.format(question, index++));
        }
    }

}
