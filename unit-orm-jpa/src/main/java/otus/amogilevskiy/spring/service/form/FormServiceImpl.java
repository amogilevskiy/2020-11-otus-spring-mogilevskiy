package otus.amogilevskiy.spring.service.form;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.service.io.IOService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private static final String ANSWER_YES = "YES";
    private static final String ANSWER_YES_SHORT = "Y";
    private static final int DEFAULT_INTEGER_ANSWER = 0;

    private final IOService ioService;
    private final LocalizationService localizationService;

    @Override
    public void showLabelField(String field) {
        ioService.out(field);
    }

    @Override
    public String showStringFormField(String field) {
        ioService.out(field);
        return ioService.in();
    }

    @Override
    public boolean showBooleanFormField(String field) {
        ioService.out(field);
        var stringValue = ioService.in();
        return stringValue != null && (stringValue.equalsIgnoreCase(ANSWER_YES_SHORT) || stringValue.equalsIgnoreCase(ANSWER_YES));
    }

    @Override
    public long showIntegerFormField(String field) {
        var stringValue = showStringFormField(field);
        try {
            return Long.parseLong(stringValue);
        } catch (NumberFormatException e) {
            ioService.out(localizationService.localize("error.notNumber"));
            return DEFAULT_INTEGER_ANSWER;
        }
    }

}
