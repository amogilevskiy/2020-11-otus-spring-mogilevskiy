package otus.amogilevskiy.spring.repository.genre;

import otus.amogilevskiy.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    boolean contains(String title);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    Optional<Genre> save(Genre genre);

}
