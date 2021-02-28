package otus.amogilevskiy.spring.service.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.genre.GenreDto;
import otus.amogilevskiy.spring.repository.GenreRepository;
import otus.amogilevskiy.spring.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;
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
        var dto = new GenreDto("new_genre");
        var expectedGenre = new Genre(null, dto.getTitle());
        when(genreRepository.save(any())).thenReturn(expectedGenre);

        genreService.create(dto);

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
