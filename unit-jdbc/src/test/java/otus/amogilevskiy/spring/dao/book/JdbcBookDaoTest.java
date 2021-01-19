package otus.amogilevskiy.spring.dao.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@JdbcTest
@Import(JdbcBookDao.class)
public class JdbcBookDaoTest {

    private static final int NON_EXISTING_BOOK_ID = 100500;

    @Autowired
    BookDao bookDao;

    @Test
    void shouldReturnAllBooks() {
        var expectedBooks = List.of(
                new Book(1, "Java 11", 1L, 1L),
                new Book(2, "Swift", 1L, 1L),
                new Book(3, "Kotlin", 2L, 1L)
        );

        var actualBooks = bookDao.findAll();

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void shouldReturnAllBooksByAuthorId() {
        var authorId = 1L;

        var expectedBooks = List.of(
                new Book(1, "Java 11", authorId, 1L),
                new Book(2, "Swift", authorId, 1L)
        );

        var actualBooks = bookDao.findAllByAuthorId(authorId);

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void shouldReturnBookById() {
        var expectedBook = new Book(1, "Java 11", 1L, 1L);

        var actualBook = bookDao.findById(1);

        assertThat(actualBook).contains(expectedBook);
    }

    @Test
    void shouldReturnEmptyIfBookNotFoundById() {
        var actualBook = bookDao.findById(NON_EXISTING_BOOK_ID);

        assertThat(actualBook).isEmpty();
    }

    @Test
    void shouldCreateBook() {
        var expectedBook = new Book(5, "Java 11", 1L, 1L);

        bookDao.create(expectedBook);
        var actualBook = bookDao.findById(expectedBook.getId());

        assertThat(actualBook).contains(expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        var id = 1L;
        var expectedBook = new Book(id, "new title", 1L, 1L);

        bookDao.update(expectedBook);
        var actualBook = bookDao.findById(id);

        assertThat(actualBook).contains(expectedBook);
    }

    @Test
    void shouldDeleteBook() {
        var initialBook = bookDao.findById(1);

        initialBook.ifPresentOrElse(book -> {
            bookDao.deleteById(book.getId());

            var actualBook = bookDao.findById(book.getId());

            assertThat(actualBook).isEmpty();
        }, () -> fail("Initial book should contain an object."));
    }

}
