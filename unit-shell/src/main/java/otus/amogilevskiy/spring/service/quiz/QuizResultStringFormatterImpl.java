package otus.amogilevskiy.spring.service.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.QuizGrade;
import otus.amogilevskiy.spring.domain.User;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class QuizResultStringFormatterImpl implements QuizResultStringFormatter {

    private final LocalizationService localizationService;

    @Override
    public String format(User user, QuizGrade grade) {
        var result = grade.isPassed() ? localizationService.localize("quiz.result.passed")
                : localizationService.localize("quiz.result.failed");
        return localizationService.localize("quiz.result.summary",
                new Object[]{user.getFirstName(), user.getLastName(), grade.getScore(), result}
        );
    }

}
