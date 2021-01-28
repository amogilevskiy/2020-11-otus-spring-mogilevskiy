package otus.amogilevskiy.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.amogilevskiy.spring.domain.Question;
import otus.amogilevskiy.spring.service.question.QuestionParser;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CsvQuestionDaoTest {

    @MockBean
    private QuestionParser parser;

    @Test
    void shouldReturnEmptyListWhenFileNotFound() {
        var dao = new CsvQuestionDao(parser, "");
        Assertions.assertTrue(dao.findAll().isEmpty());
    }

    @Test
    void shouldReturnQuestions() {
        when(parser.parse(anyString())).thenReturn(Optional.of(new Question("Test", new ArrayList<>())));

        var dao = new CsvQuestionDao(parser, "questions.csv");
        Assertions.assertFalse(dao.findAll().isEmpty());
    }

}
