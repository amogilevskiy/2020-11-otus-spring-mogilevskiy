package otus.amogilevskiy.spring.facade.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.genre.GenreService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;
import otus.amogilevskiy.spring.ui.genre.GenreView;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreFacadeImpl implements GenreFacade {

    private final FormService formService;
    private final LocalizationService localizationService;
    private final GenreService genreService;
    private final GenreView genreView;

    @Override
    public Optional<Genre> findOrCreateGenre() {
        var shouldCreateGenre = formService.showBooleanFormField(localizationService.localize("question.genre.create"));
        if (shouldCreateGenre) {
            return genreService.create(genreView.showCreateGenreView());
        } else {
            var genres = genreService.findAll();
            var genresString = genres.stream().map(Genre::toString).collect(Collectors.joining("\n"));
            formService.showLabelField(genresString);
            var genreId = formService.showIntegerFormField(localizationService.localize("question.genre.existing"));
            return genres.stream().filter(genre -> genre.getId() == genreId).findAny();
        }
    }

}
