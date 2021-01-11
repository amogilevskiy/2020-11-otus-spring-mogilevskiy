package otus.amogilevskiy.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.amogilevskiy.spring.domain.Answer;
import otus.amogilevskiy.spring.service.question.CsvQuestionParser;
import otus.amogilevskiy.spring.service.question.QuestionParser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvQuestionParserTest {

    public static final String VALID_QUESTION_DATA = "Question text?, Correct Answer, Answer";

    public static final String INVALID_QUESTION_DATA = "Question without answers";

    private QuestionParser questionParser;

    @BeforeEach
    void setUp() {
        questionParser = new CsvQuestionParser();
    }

    @Test
    void shouldReturnQuestionWhenDataValid() {
        var question = questionParser.parse(VALID_QUESTION_DATA);

        assertNotNull(question.get());
    }

    @Test
    void shouldReturnNullWhenDataInvalid() {
        var question = questionParser.parse(INVALID_QUESTION_DATA);

        assertTrue(question.isEmpty());
    }

    @Test
    void shouldReturnQuestionWithCorrectAnswer() {
        var question = questionParser.parse(VALID_QUESTION_DATA);

        assertTrue(question.get().getAnswers().stream().anyMatch(Answer::isCorrect));
    }

}
