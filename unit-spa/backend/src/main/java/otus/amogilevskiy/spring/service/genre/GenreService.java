package otus.amogilevskiy.spring.service.genre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.genre.GenreDto;

public interface GenreService {

    Genre create(GenreDto dto);

    Genre updateById(long id, GenreDto dto);

    Page<Genre> findAll(Pageable pageable);

    Genre findById(long id);

}
