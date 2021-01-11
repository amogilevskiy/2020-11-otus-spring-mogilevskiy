package otus.amogilevskiy.spring.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import otus.amogilevskiy.spring.factory.QuestionFactory;
import otus.amogilevskiy.spring.service.quiz.QuizGradeCalculatorImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizGradeCalculatorImplTest {

    private static final int QUESTIONS_COUNT_PER_TEST = 5;

    private static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(0.6, 0.4, false),
                Arguments.of(0.6, 0.6, true),
                Arguments.of(0.6, 1.0, true)
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    void shouldReturnCorrectGrade(double requiredRatio, double userRatio, boolean expectedPassed) {
        var calculator = new QuizGradeCalculatorImpl(requiredRatio);
        var results = new QuestionFactory().createQuestionResults(QUESTIONS_COUNT_PER_TEST, userRatio);

        var grade = calculator.calculateGrade(results);

        assertEquals(expectedPassed, grade.isPassed());
    }
}
