package otus.amogilevskiy.spring.dao.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.dao.TestData;
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
        var expectedBooks = TestData.allBooks();

        var actualBooks = bookDao.findAll();

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void shouldReturnAllBooksByAuthorId() {
        var author = TestData.firstAuthor();
        var genre = TestData.firstGenre();

        var expectedBooks = List.of(
                new Book(1L, "Java 11", author, genre),
                new Book(2L, "Swift", author, genre)
        );

        var actualBooks = bookDao.findAllByAuthorId(author.getId());

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void shouldReturnBookById() {
        var expectedBook = TestData.firstBook();

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
        var expectedBook = new Book(null, "New Java 11", TestData.firstAuthor(), TestData.firstGenre());

        bookDao.create(expectedBook);
        var actualBooks = bookDao.findAllByTitle(expectedBook.getTitle());

        assertThat(actualBooks).usingElementComparatorIgnoringFields("id").contains(expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        var expectedBook = new Book(1L, "Updated Java 11",
                TestData.firstAuthor(), TestData.firstGenre());

        bookDao.update(expectedBook);
        var actualBook = bookDao.findById(expectedBook.getId());

        assertThat(actualBook).get().usingRecursiveComparison().ignoringFields("authorId", "genreId").isEqualTo(expectedBook);
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
