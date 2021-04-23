package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;

@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldReturnAllBooks() {
        var actualBooks = bookRepository.findAll();

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(TestData.allBooks());
    }

    @ParameterizedTest
    @CsvSource({"1, true", "non_existing_id, false"})
    void shouldReturnTrueWhenBooksWithAuthorExist(String id, boolean expectedResult) {
        var actualResult = bookRepository.existsByAuthorsId(id);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({"1, true", "non_existing_id, false"})
    void shouldReturnTrueWhenBooksWithGenreExist(String id, boolean expectedResult) {
        var actualResult = bookRepository.existsByGenreId(id);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void shouldCreateBook() {
        var book = Book.builder()
                .title("Test")
                .build();
        var expectedBook = bookRepository.save(book);
        var actualBook = bookRepository.findById(expectedBook.getId());

        assertThat(actualBook)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringFields("genre", "authors", "comments")
                .isEqualTo(expectedBook);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1"})
    @DirtiesContext(methodMode = AFTER_METHOD)
    void shouldDeleteBook(String existingId) {
        var initialBook = bookRepository.findById(existingId);
        assertThat(initialBook).isNotNull();

        bookRepository.deleteById(existingId);
        var actualBook = bookRepository.findById(existingId);

        assertThat(actualBook).isEmpty();
    }

}

