package otus.amogilevskiy.spring.dao.genre;

import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.genre.CreateGenreDto;

import java.util.Optional;

public interface GenreDao {

    Optional<Genre> findById(long id);

    Optional<Genre> findByTitle(String title);

    boolean create(CreateGenreDto dto);

}
