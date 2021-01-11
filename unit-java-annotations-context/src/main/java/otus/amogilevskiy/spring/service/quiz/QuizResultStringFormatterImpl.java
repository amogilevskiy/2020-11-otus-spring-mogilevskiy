package otus.amogilevskiy.spring.service.quiz;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.QuizGrade;
import otus.amogilevskiy.spring.domain.User;

@Service
public class QuizResultStringFormatterImpl implements QuizResultStringFormatter {

    @Override
    public String format(User user, QuizGrade grade) {
        var result = grade.isPassed() ? "Congratulations, you passed the test."
                : "To pass the test you need a higher score.";
        return String.format("%s %s, you correctly answered %.0f%% of the questions.\n%s",
                user.getFirstName(),
                user.getLastName(),
                grade.getScore(),
                result
        );
    }

}
