package otus.amogilevskiy.spring.dao.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcGenreDao.class)
public class JdbcGenreDaoTest {

    @Autowired
    GenreDao genreDao;

    @Test
    void shouldCreateGenre() {
        var id = 2;
        var expectedGenre = new Genre(id, "New genre");

        genreDao.create(expectedGenre);
        var actualGenre = genreDao.findById(id);

        assertThat(actualGenre).contains(expectedGenre);
    }

    @Test
    void shouldReturnGenreById() {
        var expectedGenre = new Genre(1, "IT");

        var actualAuthor = genreDao.findById(expectedGenre.getId());

        assertThat(actualAuthor).contains(expectedGenre);
    }

}
