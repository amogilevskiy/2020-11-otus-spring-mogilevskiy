package otus.amogilevskiy.spring.service.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;
import otus.amogilevskiy.spring.repository.comment.CommentRepository;
import otus.amogilevskiy.spring.service.book.BookService;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@JdbcTest
@Import(CommentServiceImpl.class)
public class CommentServiceImplTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Test
    void shouldCreateNewComment() {
        var text = "new comment";
        var bookId = 1L;
        var expectedBook = TestData.firstBook();
        var expectedComment = Comment.builder()
                .text(text)
                .book(expectedBook)
                .build();

        when(bookService.findById(bookId)).thenReturn(Optional.of(expectedBook));

        commentService.create(new CommentDto(null, bookId, text));

        verify(bookService).findById(bookId);
        verify(commentRepository).save(expectedComment);
    }

    @Test
    void shouldReturnAllComments() {
        commentService.findAll();

        verify(commentRepository).findAll();
    }

    @Test
    void shouldDeleteCommentById() {
        var id = 1L;
        var expectedComment = new Comment(id, "test", TestData.firstBook());
        when(commentRepository.findById(id)).thenReturn(Optional.of(expectedComment));

        commentService.deleteById(id);

        verify(commentRepository).findById(id);
        verify(commentRepository).delete(expectedComment);
    }

    @Test
    void shouldUpdateCommentById() {
        var id = 1L;
        var dto = new CommentDto(id, null, "new title");
        var actualComment = new Comment(id, "test", TestData.firstBook());
        var expectedComment = Comment.builder()
                .id(actualComment.getId())
                .book(actualComment.getBook())
                .text(dto.getText())
                .build();
        when(commentRepository.findById(id)).thenReturn(Optional.of(expectedComment));

        commentService.update(dto);

        verify(commentRepository).findById(id);
        verify(commentRepository).save(expectedComment);
    }

    @Test
    void shouldReturnAllCommentsByBookId() {
        var bookId = 1L;
        var expectedBook = TestData.firstBook();

        when(bookService.findById(bookId)).thenReturn(Optional.of(expectedBook));

        commentService.findAllByBookId(bookId);

        verify(bookService).findById(bookId);
    }

}
