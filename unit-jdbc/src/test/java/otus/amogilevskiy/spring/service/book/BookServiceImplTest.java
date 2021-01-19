package otus.amogilevskiy.spring.service.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.amogilevskiy.spring.dao.book.BookDao;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.service.form.FormService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceImplTest {

    @MockBean
    BookDao bookDao;

    @MockBean
    FormService formService;

    @Autowired
    BookService bookService;

    @Test
    void shouldReturnNewBook() {
        var expectedIntValue = 1L;
        var expectedStringValue = "Name";

        var authorId = 1L;
        var genreId = 1L;

        var expectedBook = new Book(
                expectedIntValue,
                expectedStringValue,
                1L,
                1L
        );

        when(formService.showIntegerFormField(anyString())).thenReturn(expectedIntValue);
        when(formService.showStringFormField(anyString())).thenReturn(expectedStringValue);

        when(bookDao.findById(expectedIntValue)).thenReturn(Optional.empty());
        when(bookDao.create(any())).thenReturn(true);

        var actualBook = bookService.addBookUsingForm(authorId, genreId);

        assertThat(actualBook).contains(expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        var id = 1;
        var book = new Book(
                id,
                "old title",
                1L,
                1L
        );
        var expectedStringValue = "new title";

        when(formService.showStringFormField(anyString())).thenReturn(expectedStringValue);
        when(bookDao.findById(id)).thenReturn(Optional.of(book));

        bookService.updateBookUsingForm(id);

        verify(bookDao).update(new Book(id, expectedStringValue, 1L, 1L));
    }

}
