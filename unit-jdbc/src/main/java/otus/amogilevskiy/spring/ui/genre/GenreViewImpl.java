package otus.amogilevskiy.spring.ui.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class GenreViewImpl implements GenreView {

    private final FormService formService;
    private final LocalizationService localizationService;

    @Override
    public Genre showCreateGenreView() {
        formService.showLabelField(localizationService.localize("question.genre.form"));
        var title = formService.showStringFormField(localizationService.localize("question.genre.title"));
        return Genre.builder()
                .title(title)
                .build();
    }

}
