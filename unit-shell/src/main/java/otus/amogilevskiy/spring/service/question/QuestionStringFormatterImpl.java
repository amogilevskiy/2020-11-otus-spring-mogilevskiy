package otus.amogilevskiy.spring.service.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Answer;
import otus.amogilevskiy.spring.domain.Question;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionStringFormatterImpl implements QuestionStringFormatter {

    private final LocalizationService localizationService;

    @Override
    public String format(Question question, int index) {
        var questionTranslation = localizationService.localize(question.getText());
        return localizationService.localize("quiz.form.question.format",
                new Object[]{index, questionTranslation, formatAnswers(question.getAnswers())});
    }

    private String formatAnswers(List<Answer> answers) {
        var builder = new StringBuilder(localizationService.localize("quiz.form.answers"));
        var index = 0;
        for (var answer : answers) {
            builder.append(formatAnswer(answer, index++));
        }
        return builder.toString();
    }

    private String formatAnswer(Answer answer, int index) {
        var answerTranslation = localizationService.localize(answer.getText());
        return localizationService.localize("quiz.form.answer.format", new Object[]{index, answerTranslation});
    }

}

