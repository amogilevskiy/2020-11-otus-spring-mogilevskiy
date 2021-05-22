package otus.amogilevskiy.spring.service.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.genre.GenreDto;
import otus.amogilevskiy.spring.repository.GenreRepository;
import otus.amogilevskiy.spring.service.common.ResourceAlreadyExistsException;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;
import otus.amogilevskiy.spring.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@Import(GenreServiceImpl.class)
public class GenreServiceImplTest {

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private GenreService genreService;

    @Test
    void shouldCreateNewGenre() {
        var dto = GenreDto.builder()
                .title("new_genre")
                .build();
        var expectedGenre = new Genre(null, dto.getTitle());
        when(genreRepository.save(any())).thenReturn(expectedGenre);

        genreService.create(dto);

        verify(genreRepository).save(expectedGenre);
    }

    @Test
    void shouldReturnAllGenres() {
        var expectedGenres = TestData.allGenres();
        var pageRequest = PageRequest.of(0, 20);
        when(genreRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(expectedGenres));

        var actualPage = genreService.findAll(pageRequest);

        assertThat(actualPage.getContent()).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @Test
    void shouldRaiseGenreNotFoundException() {
        var id = 1L;

        var e = assertThrows(ResourceNotFoundException.class, () -> {
            genreService.findById(id);
        });

        assertThat(e.getMessage()).isEqualTo("genre_not_found");
    }

    @Test
    void shouldRaiseGenreAlreadyExistsException() {
        when(genreRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));

        var e = assertThrows(ResourceAlreadyExistsException.class, () -> {
            genreService.create(new GenreDto("already existing title"));
        });

        assertThat(e.getMessage()).isEqualTo("genre_already_exists");
    }

}
