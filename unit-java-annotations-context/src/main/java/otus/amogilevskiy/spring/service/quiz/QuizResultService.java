package otus.amogilevskiy.spring.service.quiz;

import otus.amogilevskiy.spring.domain.QuestionResult;
import otus.amogilevskiy.spring.domain.User;

import java.util.List;

public interface QuizResultService {

    void showResults(User user, List<QuestionResult> results);

}
