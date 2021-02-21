package otus.amogilevskiy.spring.service.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.dto.book.BookDto;
import otus.amogilevskiy.spring.repository.book.BookRepository;
import otus.amogilevskiy.spring.service.author.AuthorService;
import otus.amogilevskiy.spring.service.comment.CommentService;
import otus.amogilevskiy.spring.service.genre.GenreService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@JdbcTest
@Import(BookServiceImpl.class)
public class BookServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private LocalizationService localizationService;

    @Autowired
    private BookService bookService;

    @Test
    void shouldCreateNewBook() {
        var title = "New book";
        var genreId = TestData.firstGenre().getId();
        var authorId = TestData.FIRST_AUTHOR_ID;

        when(authorService.findById(authorId)).thenReturn(Optional.of(TestData.firstAuthor()));
        when(genreService.findById(genreId)).thenReturn(Optional.of(TestData.firstGenre()));

        var dto = BookDto.builder()
                .title(title)
                .genreId(genreId)
                .authorId(authorId)
                .build();
        bookService.create(dto);

        verify(genreService).findById(dto.getGenreId());
        verify(authorService).findById(dto.getAuthorId());
        verify(bookRepository).save(any());
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

        var actualResult = bookService.deleteById(book.getId());

        assertThat(actualResult).isEqualTo(true);
    }

}
