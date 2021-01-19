package otus.amogilevskiy.spring.service.genre;

import otus.amogilevskiy.spring.domain.Genre;

import java.util.Optional;

public interface GenreService {

    Optional<Genre> addGenreUsingForm();

}
