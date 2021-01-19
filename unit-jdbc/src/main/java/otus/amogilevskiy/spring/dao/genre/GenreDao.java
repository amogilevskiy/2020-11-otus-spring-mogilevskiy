package otus.amogilevskiy.spring.dao.genre;

import otus.amogilevskiy.spring.domain.Genre;

import java.util.Optional;

public interface GenreDao {

    Optional<Genre> findById(long id);

    boolean create(Genre genre);

}
