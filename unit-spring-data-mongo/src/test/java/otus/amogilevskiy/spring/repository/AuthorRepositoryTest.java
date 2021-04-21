package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;

@DataMongoTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void shouldReturnAllAuthors() {
        var actualAuthors = authorRepository.findAll();

        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(TestData.allAuthors());
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void shouldCreateAuthor() {
        var author = Author.builder()
                .firstName("Test")
                .lastName("Test")
                .build();
        var expectedAuthor = authorRepository.save(author);
        var actualAuthor = authorRepository.findById(expectedAuthor.getId());

        assertThat(actualAuthor).isPresent().contains(expectedAuthor);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1"})
    @DirtiesContext(methodMode = AFTER_METHOD)
    void shouldDeleteAuthor(String existingId) {
        var initialAuthor = authorRepository.findById(existingId);
        assertThat(initialAuthor).isNotNull();

        authorRepository.deleteById(existingId);
        var actualAuthor = authorRepository.findById(existingId);

        assertThat(actualAuthor).isEmpty();
    }

}