package otus.amogilevskiy.spring.ui.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class GenreViewImpl implements GenreView {

    private final LocalizationService localizationService;

    @Override
    public String showGenreDetailView(Genre genre) {
        return localizationService.localize("genre.detail", new Object[]{genre.getId(), genre.getTitle()});
    }
}
