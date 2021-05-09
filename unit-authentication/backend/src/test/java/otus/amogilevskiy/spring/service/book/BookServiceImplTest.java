package otus.amogilevskiy.spring.service.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.BookDto;
import otus.amogilevskiy.spring.repository.BookRepository;
import otus.amogilevskiy.spring.service.author.AuthorService;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;
import otus.amogilevskiy.spring.service.genre.GenreService;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@Import(BookServiceImpl.class)
public class BookServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Test
    void shouldCreateNewBook() {
        var title = "New book";
        var genreId = TestData.firstGenre().getId();
        var authorId = TestData.FIRST_AUTHOR_ID;

        var dto = BookDto.builder()
                .title(title)
                .genreId(genreId)
                .authorIds(Set.of(authorId))
                .build();

        var expectedBook = Book.builder()
                .title(title)
                .authors(Set.of(TestData.firstAuthor()))
                .genre(TestData.firstGenre())
                .build();

        when(authorService.findAllById(Set.of(authorId))).thenReturn(List.of(TestData.firstAuthor()));
        when(genreService.findById(genreId)).thenReturn(TestData.firstGenre());
        when(bookRepository.save(any())).thenReturn(expectedBook);


        bookService.create(dto);

        verify(genreService).findById(dto.getGenreId());
        verify(authorService).findAllById(dto.getAuthorIds());
        verify(bookRepository).save(any());
    }

    @Test
    void shouldReturnAllBooks() {
        var expectedBooks = TestData.allBooks();
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(expectedBooks));

        var actualBooks = bookService.findAll(PageRequest.of(0, 20));

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void shouldDeleteBookById() {
        var book = TestData.firstBook();

        var actualResult = bookService.deleteById(book.getId());

        verify(bookRepository).deleteById(book.getId());
        assertThat(actualResult).isTrue();
    }

    @Test
    void shouldRaiseBookNotFoundException() {
        var id = 1L;

        var e = assertThrows(ResourceNotFoundException.class, () -> {
            bookService.findById(id);
        });

        assertThat(e.getMessage()).isEqualTo("book_not_found");
    }

}
