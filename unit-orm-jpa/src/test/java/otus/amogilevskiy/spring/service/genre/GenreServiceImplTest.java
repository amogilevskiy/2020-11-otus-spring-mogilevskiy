package otus.amogilevskiy.spring.service.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.genre.GenreDto;
import otus.amogilevskiy.spring.repository.genre.GenreRepository;
import otus.amogilevskiy.spring.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@JdbcTest
@Import(GenreServiceImpl.class)
public class GenreServiceImplTest {

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private GenreService genreService;

    @Test
    void shouldCreateNewGenre() {
        var dto = new GenreDto("new_genre");
        var expectedGenre = new Genre(null, dto.getTitle());

        var actualGenre = genreService.create(dto);

        verify(genreRepository).save(expectedGenre);
    }

    @Test
    void shouldReturnAllAuthors() {
        var expectedGenres = TestData.allGenres();
        when(genreRepository.findAll()).thenReturn(expectedGenres);

        var actualGenres = genreService.findAll();

        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }
}
