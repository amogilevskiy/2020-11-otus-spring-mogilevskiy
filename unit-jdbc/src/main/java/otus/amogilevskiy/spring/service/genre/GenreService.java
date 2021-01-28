package otus.amogilevskiy.spring.service.genre;

import otus.amogilevskiy.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> create(Genre genre);

    List<Genre> findAll();

}
