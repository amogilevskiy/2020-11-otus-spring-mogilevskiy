package otus.amogilevskiy.spring.service.question;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Answer;
import otus.amogilevskiy.spring.domain.Question;

import java.util.List;

@Service
public class QuestionStringFormatterImpl implements QuestionStringFormatter {

    @Override
    public String format(Question question, int index) {
        return String.format("%d. %s \n%s\n", index, question.getText(), formatAnswers(question.getAnswers()));
    }

    private String formatAnswers(List<Answer> answers) {
        var builder = new StringBuilder("\n\tAnswers:\n");
        var index = 0;
        for (var answer : answers) {
            builder.append(formatAnswer(answer, index++));
        }
        return builder.toString();
    }

    private String formatAnswer(Answer answer, int index) {
        return String.format("\n\t%d. %s", index, answer.getText());
    }

}

