package otus.amogilevskiy.spring.service.quiz;

import otus.amogilevskiy.spring.domain.QuestionResult;
import otus.amogilevskiy.spring.domain.QuizGrade;

import java.util.List;

public interface QuizGradeCalculator {

    QuizGrade calculateGrade(List<QuestionResult> results);

}
