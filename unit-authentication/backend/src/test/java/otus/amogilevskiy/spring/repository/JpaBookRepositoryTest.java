package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
    void shouldReturnAllBooks() {
        var expectedBooks = TestData.allBooks();

        var actualBooks = bookRepository.findAll();

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void shouldCreateBook() {
        var genre = em.find(Genre.class, TestData.FIRST_GENRE_ID);
        var author = em.find(Author.class, TestData.FIRST_AUTHOR_ID);

        var expectedBook = new Book(null, "new title", Set.of(author), genre, List.of());

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
        initialBook.setTitle(updatedTitle);
        em.clear();

        bookRepository.save(initialBook);
        var actualBook = em.find(Book.class, id);

        assertThat(actualBook.getTitle()).isEqualTo(updatedTitle);
    }

    @Test
    void shouldReturnBookWithComments() {
        var firstComment = em.find(Comment.class, TestData.FIRST_COMMENT_ID);
        var secondComment = em.find(Comment.class, TestData.SECOND_COMMENT_ID);

        var actualComments = bookRepository.findById(TestData.FIRST_BOOK_ID).map(Book::getComments).orElse(List.of());

        assertThat(actualComments).containsExactlyInAnyOrder(firstComment, secondComment);
    }

    @Test
    void shouldDeleteBookCommentsWhenItDeleted() {
        var bookId = 1L;
        var query = em.getEntityManager().createQuery("select c from Comment c where c.book.id = :book_id", Comment.class);
        query.setParameter("book_id", bookId);
        var initialComments = query.getResultList();

        assertThat(initialComments).hasSizeGreaterThan(0);

        bookRepository.deleteById(bookId);

        var actualComments = query.getResultList();

        assertThat(actualComments).hasSize(0);
    }

}
