package otus.amogilevskiy.spring.service.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.QuestionResult;
import otus.amogilevskiy.spring.domain.User;
import otus.amogilevskiy.spring.service.io.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizResultServiceImpl implements QuizResultService {

    private final QuizGradeCalculator quizGradeCalculator;

    private final QuizResultStringFormatter quizResultStringFormatter;

    private final IOService ioService;

    @Override
    public void showResults(User user, List<QuestionResult> results) {
        if (results.size() > 0) {
            var grade = quizGradeCalculator.calculateGrade(results);
            ioService.out(quizResultStringFormatter.format(user, grade));
        }
    }

}
