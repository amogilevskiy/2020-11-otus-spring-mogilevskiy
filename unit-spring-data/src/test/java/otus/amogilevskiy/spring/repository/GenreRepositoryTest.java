package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldCreateGenre() {
        var expectedGenre = new Genre(null, "New genre");

        genreRepository.save(expectedGenre);

        var actualGenre = em.find(Genre.class, TestData.NUMBER_OF_ALL_GENRES + 1);

        assertThat(actualGenre).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedGenre);
    }

    @Test
    void shouldReturnGenreById() {
        var expectedGenre = em.find(Genre.class, TestData.FIRST_GENRE_ID);

        var actualGenre = genreRepository.findById(expectedGenre.getId());

        assertThat(actualGenre).isPresent().contains(expectedGenre);
    }

    @Test
    void shouldReturnAllGenres() {
        var expectedGenres = TestData.allGenres();

        var actualGenres = genreRepository.findAll();

        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

}
