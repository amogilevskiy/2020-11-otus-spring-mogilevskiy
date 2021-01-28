package otus.amogilevskiy.spring.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dao.genre.GenreDao;
import otus.amogilevskiy.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Optional<Genre> create(Genre genre) {
        return genreDao.create(genre).flatMap(genreDao::findById);
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }
}
