package otus.amogilevskiy.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.BookDto;
import otus.amogilevskiy.spring.repository.BookRepository;
import otus.amogilevskiy.spring.service.author.AuthorService;
import otus.amogilevskiy.spring.service.book.BookServiceImpl;
import otus.amogilevskiy.spring.service.genre.GenreService;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreService genreService;

    @Mock
    private AuthorService authorService;

    @Test
    void shouldCreateNewBook() {
        var title = "New book";
        var genreId = TestData.firstGenre().getId();
        var authorId = "";

        var dto = BookDto.builder()
                .title(title)
                .genreId(genreId)
                .authorId(authorId)
                .build();

        var expectedBook = Book.builder()
                .title(title)
                .authors(Set.of(TestData.firstAuthor()))
                .genre(TestData.firstGenre())
                .build();

        when(authorService.findById(authorId)).thenReturn(Optional.of(TestData.firstAuthor()));
        when(genreService.findById(genreId)).thenReturn(Optional.of(TestData.firstGenre()));
        when(bookRepository.save(any())).thenReturn(expectedBook);


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

        var actualResult = bookService.deleteById(book.getId());

        verify(bookRepository).deleteById(book.getId());
        assertThat(actualResult).isTrue();
    }

}
