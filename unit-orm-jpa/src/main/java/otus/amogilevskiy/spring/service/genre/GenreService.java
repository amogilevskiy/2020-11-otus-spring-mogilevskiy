package otus.amogilevskiy.spring.service.genre;

import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.genre.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> create(GenreDto dto);

    List<Genre> findAll();

    Optional<Genre> findById(long id);
    
}
