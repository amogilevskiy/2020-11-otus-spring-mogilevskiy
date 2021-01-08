package otus.amogilevskiy.spring.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import otus.amogilevskiy.spring.domain.Question;
import otus.amogilevskiy.spring.service.QuestionParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvQuestionDao implements QuestionDao {

    private final QuestionParser parser;

    private final Resource resource;

    public CsvQuestionDao(QuestionParser parser, String filePath) {
        this.parser = parser;
        this.resource = new ClassPathResource(filePath);
    }

    @Override
    public List<Question> findAll() {
        var questions = new ArrayList<Question>();
        try (Scanner scanner = new Scanner(resource.getInputStream())) {
            while (scanner.hasNextLine()) {
                parser.parse(scanner.nextLine()).ifPresent(questions::add);
            }
        } catch (IOException e) {
            return questions;
        }
        return questions;
    }

}
