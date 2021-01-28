package otus.amogilevskiy.spring.service.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.dao.TestData;
import otus.amogilevskiy.spring.dao.genre.GenreDao;
import otus.amogilevskiy.spring.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@JdbcTest
@Import(GenreServiceImpl.class)
public class GenreServiceImplTest {

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private GenreService genreService;

    @Test
    void shouldReturnNewGenre() {
        var genre = new Genre(null, "new_genre");

        var expectedId = 1L;
        var expectedGenre = new Genre(expectedId, genre.getTitle());

        when(genreDao.create(genre)).thenReturn(Optional.of(expectedId));
        when(genreDao.findById(expectedId)).thenReturn(Optional.of(expectedGenre));

        var actualGenre = genreService.create(genre);

        assertThat(actualGenre).contains(expectedGenre);
    }

    @Test
    void shouldReturnAllAuthors() {
        var expectedGenres = TestData.allGenres();
        when(genreDao.findAll()).thenReturn(expectedGenres);

        var actualGenres = genreService.findAll();

        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }
}
