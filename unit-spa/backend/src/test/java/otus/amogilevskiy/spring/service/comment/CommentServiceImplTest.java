package otus.amogilevskiy.spring.service.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;
import otus.amogilevskiy.spring.repository.CommentRepository;
import otus.amogilevskiy.spring.service.book.BookService;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
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

        when(bookService.findById(bookId)).thenReturn(expectedBook);
        when(commentRepository.save(any())).thenReturn(expectedComment);

        var dto = CommentDto.builder()
                .text(text)
                .bookId(bookId)
                .build();

        commentService.create(dto);

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

        var actualResult = commentService.deleteById(id);

        verify(commentRepository).deleteById(id);
        assertThat(actualResult).isTrue();
    }

    @Test
    void shouldUpdateCommentById() {
        var id = 1L;
        var actualComment = new Comment(id, "test", TestData.firstBook());
        var dto = new CommentDto(actualComment.getBook().getId(), "new title");
        var expectedComment = Comment.builder()
                .id(actualComment.getId())
                .book(actualComment.getBook())
                .text(dto.getText())
                .build();
        when(commentRepository.findById(id)).thenReturn(Optional.of(expectedComment));

        commentService.updateById(id, dto);

        verify(commentRepository).findById(id);
        verify(commentRepository).save(expectedComment);
    }

    @Test
    void shouldReturnAllCommentsByBookId() {
        var bookId = 1L;
        var pageRequest = PageRequest.of(0, 20);

        var comments = TestData.allCommentFromFirstBook();

        when(commentRepository.findByBook_Id(bookId, pageRequest)).thenReturn(new PageImpl<>(comments));

        commentService.findByBookId(bookId, pageRequest);

        verify(commentRepository).findByBook_Id(bookId, pageRequest);
    }

    @Test
    void shouldRaiseCommentNotFoundException() {
        var id = 1L;

        var e = assertThrows(ResourceNotFoundException.class, () -> {
            commentService.findById(id);
        });

        assertThat(e.getMessage()).isEqualTo("comment_not_found");
    }

}
