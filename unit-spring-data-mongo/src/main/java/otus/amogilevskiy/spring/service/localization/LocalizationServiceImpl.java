package otus.amogilevskiy.spring.service.localization;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;

    @Override
    public String localize(String key) {
        return localize(key, new Object[0]);
    }

    @Override
    public String localize(String key, Object[] args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

}
