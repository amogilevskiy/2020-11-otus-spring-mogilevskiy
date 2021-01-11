package otus.amogilevskiy.spring.service.question;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dao.QuestionDao;
import otus.amogilevskiy.spring.domain.Question;
import otus.amogilevskiy.spring.domain.QuestionResult;
import otus.amogilevskiy.spring.service.io.IOService;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    private final QuestionStringFormatter questionStringFormatter;

    private final IOService ioService;

    public QuestionServiceImpl(QuestionDao questionDao,
                               QuestionStringFormatter questionStringFormatter,
                               IOService ioService) {
        this.questionDao = questionDao;
        this.questionStringFormatter = questionStringFormatter;
        this.ioService = ioService;
    }

    @Override
    public List<QuestionResult> showQuestionsForm() {
        var questions = questionDao.findAll();
        if (questions.size() == 0) {
            showError("Questions not found.");
            return new ArrayList<>();
        } else {
            return showQuestions(questions);
        }
    }

    private void showError(String message) {
        ioService.out(message);
    }

    private List<QuestionResult> showQuestions(List<Question> questions) {
        var results = new ArrayList<QuestionResult>(questions.size());
        var index = 1;
        for (var question : questions) {
            ioService.out(questionStringFormatter.format(question, index++));
            var userAnswer = ioService.in();
            results.add(new QuestionResult(question, userAnswer));
        }
        return results;
    }

}
