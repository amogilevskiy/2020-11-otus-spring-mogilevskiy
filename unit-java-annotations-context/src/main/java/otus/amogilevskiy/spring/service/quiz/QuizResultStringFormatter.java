package otus.amogilevskiy.spring.service.quiz;

import otus.amogilevskiy.spring.domain.QuizGrade;
import otus.amogilevskiy.spring.domain.User;

public interface QuizResultStringFormatter {

    String format(User user, QuizGrade grade);

}
