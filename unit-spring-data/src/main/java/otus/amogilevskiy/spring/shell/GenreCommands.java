package otus.amogilevskiy.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.amogilevskiy.spring.dto.genre.GenreDto;
import otus.amogilevskiy.spring.service.genre.GenreService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;
import otus.amogilevskiy.spring.ui.genre.GenreView;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    private final GenreService genreService;
    private final GenreView genreView;
    private final LocalizationService localizationService;

    @ShellMethod(value = "Add a new genre", key = {"ag", "add-genre"})
    public String addGenre(@ShellOption String text) {
        return genreService.create(new GenreDto(text))
                .map(genreView::showGenreDetailView)
                .orElse(localizationService.localize("error.genreNotAdded"));
    }

    @ShellMethod(value = "List all genres", key = {"fag", "find-genres"})
    public String findAllGenres() {
        return genreService.findAll().stream().map(genreView::showGenreDetailView).collect(Collectors.joining("\n"));
    }

}
