package otus.amogilevskiy.spring.dao;

import otus.amogilevskiy.spring.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAll();

}
