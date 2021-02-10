package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.repository.book.BookRepository;
import otus.amogilevskiy.spring.repository.book.JpaBookRepository;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaBookRepository.class)
public class JpaBookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnBookById() {
        var expectedBook = em.find(Book.class, TestData.FIRST_GENRE_ID);

        var actualBook = bookRepository.findById(expectedBook.getId());

        assertThat(actualBook).isPresent().contains(expectedBook);
    }

    @Test
    void shouldReturnAllGenres() {
        var expectedBooks = TestData.allBooks();

        var actualBooks = bookRepository.findAll();

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void shouldCreateBook() {
        var expectedBook = new Book(null, "new title", Set.of(TestData.firstAuthor()), TestData.firstGenre());

        bookRepository.save(expectedBook);

        var actualBook = em.find(Book.class, TestData.NUMBER_OF_ALL_BOOKS + 1);

        assertThat(actualBook).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedBook);
    }

    @Test
    void shouldDeleteBookById() {
        var book = em.find(Book.class, TestData.SECOND_BOOK_ID);

        em.clear();
        bookRepository.deleteById(book.getId());

        var actualResult = em.find(Book.class, book.getId());

        assertThat(actualResult).isNull();
    }

    @Test
    void shouldUpdateBookTitleById() {
        var id = TestData.FIRST_BOOK_ID;

        var initialBook = em.find(Book.class, id);
        var updatedTitle = "new text";

        assertThat(initialBook.getTitle()).isNotEqualTo(updatedTitle);
        em.clear();
        bookRepository.updateTitleById(id, updatedTitle);
        var actualBook = em.find(Book.class, id);

        assertThat(actualBook.getTitle()).isEqualTo(updatedTitle);
    }

}
