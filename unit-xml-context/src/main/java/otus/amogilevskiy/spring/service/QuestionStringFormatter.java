package otus.amogilevskiy.spring.service;

import otus.amogilevskiy.spring.domain.Question;

public interface QuestionStringFormatter {

    String format(Question question, int index);

}
