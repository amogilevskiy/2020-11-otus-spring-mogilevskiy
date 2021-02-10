package otus.amogilevskiy.spring.service.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.utils.TestData;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.repository.genre.GenreRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@JdbcTest
@Import(GenreServiceImpl.class)
public class GenreServiceImplTest {

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private GenreService genreService;

    @Test
    void shouldReturnNewGenre() {
        var genre = new Genre(null, "new_genre");

        var expectedId = 1L;
        var expectedGenre = new Genre(expectedId, genre.getTitle());

        when(genreRepository.save(genre)).thenReturn(Optional.of(expectedGenre));
        when(genreRepository.findById(expectedId)).thenReturn(Optional.of(expectedGenre));

        var actualGenre = genreService.create(genre);

        assertThat(actualGenre).contains(expectedGenre);
    }

    @Test
    void shouldReturnAllAuthors() {
        var expectedGenres = TestData.allGenres();
        when(genreRepository.findAll()).thenReturn(expectedGenres);

        var actualGenres = genreService.findAll();

        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }
}
