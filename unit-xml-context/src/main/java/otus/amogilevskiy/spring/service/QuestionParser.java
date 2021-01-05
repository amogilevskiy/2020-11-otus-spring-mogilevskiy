package otus.amogilevskiy.spring.service;

import otus.amogilevskiy.spring.domain.Question;

import java.util.Optional;

public interface QuestionParser {

    Optional<Question> parse(String data);

}
