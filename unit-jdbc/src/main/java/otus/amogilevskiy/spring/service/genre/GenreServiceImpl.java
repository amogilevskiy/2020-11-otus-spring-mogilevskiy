package otus.amogilevskiy.spring.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dao.genre.GenreDao;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final FormService formService;
    private final GenreDao genreDao;
    private final LocalizationService localizationService;

    @Override
    public Optional<Genre> addGenreUsingForm() {
        formService.showLabelField(localizationService.localize("question.genre.form"));

        var genre = createGenreUsingForm();
        if (genreDao.findById(genre.getId()).isPresent()) {
            formService.showLabelField(localizationService.localize("error.genreAlreadyExists"));
        } else if (genreDao.create(genre)) {
            return Optional.of(genre);
        }
        return Optional.empty();
    }

    private Genre createGenreUsingForm() {
        var id = formService.showIntegerFormField(localizationService.localize("question.genre.id"));
        var title = formService.showStringFormField(localizationService.localize("question.genre.title"));
        return new Genre(id, title);
    }

}
