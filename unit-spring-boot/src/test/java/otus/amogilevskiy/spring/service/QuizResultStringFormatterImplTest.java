package otus.amogilevskiy.spring.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import otus.amogilevskiy.spring.domain.QuizGrade;
import otus.amogilevskiy.spring.domain.User;
import otus.amogilevskiy.spring.service.quiz.QuizResultStringFormatterImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class QuizResultStringFormatterImplTest {

    @Autowired
    QuizResultStringFormatterImpl formatter;

    private static Stream<Arguments> params() {
        return Stream.of(
                createQuizResultArguments(60,
                        true,
                        "Tester Tester, you correctly answered 60% of the questions.\nCongratulations, you passed the test."),
                createQuizResultArguments(0,
                        false,
                        "Tester Tester, you correctly answered 0% of the questions.\nTo pass the test you need a higher score.")
        );
    }

    private static Arguments createQuizResultArguments(double score, boolean passed, String expectedString) {
        return Arguments.of(createUser(), new QuizGrade(score, passed), expectedString);
    }

    private static User createUser() {
        return new User("Tester", "Tester");
    }

    @ParameterizedTest
    @MethodSource("params")
    void shouldReturnCorrectString(User user, QuizGrade grade, String expectedString) {
        assertEquals(expectedString, formatter.format(user, grade));
    }
}
