package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import otus.amogilevskiy.spring.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;

@DataMongoTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void shouldReturnAllGenres() {
        var actualGenres = genreRepository.findAll();

        assertThat(actualGenres).hasSize(1);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void shouldCreateGenre() {
        var genre = Genre.builder()
                .title("Test")
                .build();
        var expectedGenre = genreRepository.save(genre);
        var actualGenre = genreRepository.findById(expectedGenre.getId());

        assertThat(actualGenre).isPresent().contains(expectedGenre);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1"})
    @DirtiesContext(methodMode = AFTER_METHOD)
    void shouldDeleteGenre(String existingId) {
        var initialGenre = genreRepository.findById(existingId);
        assertThat(initialGenre).isNotNull();

        genreRepository.deleteById(existingId);
        var actualGenre = genreRepository.findById(existingId);

        assertThat(actualGenre).isEmpty();
    }

}
