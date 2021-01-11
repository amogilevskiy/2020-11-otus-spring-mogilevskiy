package otus.amogilevskiy.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import otus.amogilevskiy.spring.domain.Question;
import otus.amogilevskiy.spring.service.question.QuestionParser;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CsvQuestionDaoTest {

    @Test
    void shouldReturnEmptyListWhenFileNotFound() {
        var parser = mock(QuestionParser.class);
        var dao = new CsvQuestionDao(parser, "");
        Assertions.assertTrue(dao.findAll().isEmpty());
    }

    @Test
    void shouldReturnQuestions() {
        var parser = mock(QuestionParser.class);
        when(parser.parse(anyString())).thenReturn(Optional.of(new Question("Test", new ArrayList<>())));

        var dao = new CsvQuestionDao(parser, "test-questions.csv");
        Assertions.assertFalse(dao.findAll().isEmpty());
    }

}
