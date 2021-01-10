package otus.amogilevskiy.spring.service;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Answer;
import otus.amogilevskiy.spring.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CsvQuestionParser implements QuestionParser {

    private static final int QUESTION_TEXT_INDEX = 0;
    private static final int ANSWERS_START_INDEX = 1;
    private static final int CORRECT_ANSWER_INDEX = 1;
    private static final int REQUIRED_NUMBER_OF_ANSWERS = 2;
    private static final int REQUIRED_NUMBER_OF_VALUES = REQUIRED_NUMBER_OF_ANSWERS + 1;

    @Override
    public Optional<Question> parse(String data) {
        if (data != null) {
            var values = Arrays.asList(data.split(","));
            if (values.size() >= REQUIRED_NUMBER_OF_VALUES) {
                var text = values.get(QUESTION_TEXT_INDEX);
                var answers = parseAnswers(values);
                if (answers.size() >= REQUIRED_NUMBER_OF_ANSWERS) {
                    return Optional.of(new Question(text, answers));
                }
            }
        }
        return Optional.empty();
    }

    private List<Answer> parseAnswers(List<String> values) {
        var answers = new ArrayList<Answer>(values.size());
        for (var i = ANSWERS_START_INDEX; i < values.size(); i++) {
            var text = values.get(i);
            answers.add(new Answer(text, i == CORRECT_ANSWER_INDEX));
        }
        return answers;
    }

}
