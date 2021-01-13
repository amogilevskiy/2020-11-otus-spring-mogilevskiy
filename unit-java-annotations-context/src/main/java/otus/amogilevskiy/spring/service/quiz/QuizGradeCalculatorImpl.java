package otus.amogilevskiy.spring.service.quiz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.QuestionResult;
import otus.amogilevskiy.spring.domain.QuizGrade;

import java.util.List;

@Service
public class QuizGradeCalculatorImpl implements QuizGradeCalculator {

    private static final int DEFAULT_USER_ANSWER_INDEX = 0;

    private final double passedRequiredRatio;

    public QuizGradeCalculatorImpl(@Value("${grade.passed.ratio}") double passedRequiredRatio) {
        this.passedRequiredRatio = passedRequiredRatio;
    }

    @Override
    public QuizGrade calculateGrade(List<QuestionResult> results) {
        var totalResults = results.size();
        if (totalResults > 0) {

            var correctResults = 0;

            for (var result : results) {
                var iterator = result.getQuestion().getAnswers().listIterator();
                while (iterator.hasNext()) {
                    var index = iterator.nextIndex();

                    var userAnswerAsIndex = getIndexFromUserAnswer(result);
                    if (iterator.next().isCorrect() && index == userAnswerAsIndex) {
                        correctResults++;
                    }
                }
            }

            var ratio = (double) correctResults / totalResults;
            var score = ratio * 100;
            return new QuizGrade(score, Double.compare(ratio, passedRequiredRatio) > -1);
        }
        return QuizGrade.empty();
    }

    private int getIndexFromUserAnswer(QuestionResult result) {
        try {
            return Integer.parseInt(result.getUserAnswer());
        } catch (NumberFormatException e) {
            return DEFAULT_USER_ANSWER_INDEX;
        }
    }

}
