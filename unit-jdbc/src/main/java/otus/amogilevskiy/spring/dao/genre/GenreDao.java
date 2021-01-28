package otus.amogilevskiy.spring.dao.genre;

import otus.amogilevskiy.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    boolean contains(String title);

    Optional<Genre> findById(long id);

    Optional<Genre> findByTitle(String title);

    List<Genre> findAll();

    Optional<Long> create(Genre genre);

}
