package otus.amogilevskiy.spring.service.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.repository.book.BookRepository;
import otus.amogilevskiy.spring.service.comment.CommentService;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@JdbcTest
@Import(BookServiceImpl.class)
public class BookServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentService commentService;

    @Autowired
    private BookService bookService;

    @Test
    void shouldReturnTrueWhenBookCreated() {
        var book = new Book(null, "New book", Set.of(TestData.firstAuthor()), TestData.firstGenre());
        when(bookRepository.save(book)).thenReturn(Optional.of(book));

        var actualResult = bookService.create(book);

        assertThat(actualResult).isEqualTo(true);
    }

    @Test
    void shouldReturnAllBooks() {
        var expectedBooks = TestData.allBooks();
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        var actualBooks = bookService.findAll();

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void shouldReturnTrueWhenBookDeleted() {
        var book = TestData.firstBook();
        when(bookRepository.deleteById(book.getId())).thenReturn(true);
        when(commentService.deleteAllByBookId(book.getId())).thenReturn(true);

        var actualResult = bookService.deleteById(book.getId());

        assertThat(actualResult).isEqualTo(true);
    }

}
