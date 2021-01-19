package otus.amogilevskiy.spring.service.quiz;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.QuestionResult;
import otus.amogilevskiy.spring.domain.User;
import otus.amogilevskiy.spring.service.io.IOService;

import java.util.List;

@Service
public class QuizResultServiceImpl implements QuizResultService {

    private final QuizGradeCalculator quizGradeCalculator;

    private final QuizResultStringFormatter quizResultStringFormatter;

    private final IOService ioService;

    public QuizResultServiceImpl(QuizGradeCalculator quizGradeCalculator,
                                 QuizResultStringFormatter quizResultStringFormatter,
                                 IOService ioService) {
        this.quizGradeCalculator = quizGradeCalculator;
        this.quizResultStringFormatter = quizResultStringFormatter;
        this.ioService = ioService;
    }

    @Override
    public void showResults(User user, List<QuestionResult> results) {
        if (results.size() > 0) {
            var grade = quizGradeCalculator.calculateGrade(results);
            ioService.out(quizResultStringFormatter.format(user, grade));
        }
    }

}
