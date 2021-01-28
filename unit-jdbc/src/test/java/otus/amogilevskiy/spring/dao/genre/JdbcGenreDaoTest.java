package otus.amogilevskiy.spring.dao.genre;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.dao.TestData;
import otus.amogilevskiy.spring.domain.Genre;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcGenreDao.class)
public class JdbcGenreDaoTest {

    @Autowired
    GenreDao genreDao;

    private static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("IT", true),
                Arguments.of("NON EXISTING GENRE", false)
        );
    }

    @Test
    void shouldCreateGenre() {
        var expectedGenre = new Genre(null, "New genre");

        genreDao.create(expectedGenre);
        var actualGenre = genreDao.findByTitle(expectedGenre.getTitle());

        assertThat(actualGenre.get()).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedGenre);
    }

    @Test
    void shouldReturnGenreById() {
        var expectedGenre = new Genre(1L, "IT");

        var actualAuthor = genreDao.findById(expectedGenre.getId());

        assertThat(actualAuthor).contains(expectedGenre);
    }

    @Test
    void shouldReturnAllGenres() {
        var expectedGenres = TestData.allGenres();

        var actualGenres = genreDao.findAll();

        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @ParameterizedTest
    @MethodSource("params")
    void shouldReturnCorrectResult(String title, boolean expectedResult) {
        var actualResult = genreDao.contains(title);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

}
