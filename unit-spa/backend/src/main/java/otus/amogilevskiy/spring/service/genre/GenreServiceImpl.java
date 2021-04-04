package otus.amogilevskiy.spring.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.genre.GenreDto;
import otus.amogilevskiy.spring.repository.GenreRepository;
import otus.amogilevskiy.spring.service.common.Resource;
import otus.amogilevskiy.spring.service.common.ResourceAlreadyExistsException;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public Genre create(GenreDto dto) {
        var genre = Genre.builder()
                .title(dto.getTitle())
                .build();
        try {
            return genreRepository.save(genre);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(Resource.GENRE);
        }
    }

    @Transactional
    @Override
    public Genre updateById(long id, GenreDto dto) {
        var genre = Genre.builder()
                .id(id)
                .title(dto.getTitle())
                .build();
        return genreRepository.save(genre);
    }

    @Override
    public Genre findById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Resource.GENRE));
    }

    @Override
    public Page<Genre> findAll(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }

}
