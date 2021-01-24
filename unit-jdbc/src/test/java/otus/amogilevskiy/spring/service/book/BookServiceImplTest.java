package otus.amogilevskiy.spring.service.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.dao.TestData;
import otus.amogilevskiy.spring.dao.book.BookDao;
import otus.amogilevskiy.spring.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@JdbcTest
@Import(BookServiceImpl.class)
public class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @Autowired
    private BookService bookService;

    @Test
    void shouldReturnTrueWhenBookCreated() {
        var book = new Book(null, "New book", TestData.firstAuthor(), TestData.firstGenre());
        when(bookDao.create(book)).thenReturn(true);

        var actualResult = bookService.create(book);

        assertThat(actualResult).isEqualTo(true);
    }

    @Test
    void shouldReturnAllBooks() {
        var expectedBooks = TestData.allBooks();
        when(bookDao.findAll()).thenReturn(expectedBooks);

        var actualBooks = bookService.findAll();

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void shouldReturnTrueWhenBookDeleted() {
        var book = TestData.firstBook();
        when(bookDao.deleteById(book.getId())).thenReturn(true);

        var actualResult = bookService.deleteById(book.getId());

        assertThat(actualResult).isEqualTo(true);
    }

}
