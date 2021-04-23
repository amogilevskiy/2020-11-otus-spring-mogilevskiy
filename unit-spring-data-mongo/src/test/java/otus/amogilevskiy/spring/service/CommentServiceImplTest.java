package otus.amogilevskiy.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;
import otus.amogilevskiy.spring.repository.BookRepository;
import otus.amogilevskiy.spring.repository.CommentRepository;
import otus.amogilevskiy.spring.service.comment.CommentServiceImpl;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void shouldCreateNewComment() {
        var bookId = "1";
        var dto = new CommentDto(bookId, "test text");
        var expectedBook = Book.builder()
                .id(bookId)
                .title("test")
                .build();
        var expectedComment = Comment.builder()
                .text(dto.getText())
                .book(expectedBook)
                .build();
        when(bookRepository.findById(expectedBook.getId())).thenReturn(Optional.of(expectedBook));

        commentService.create(dto);

        verify(commentRepository).save(expectedComment);
    }

    @Test
    void shouldReturnAllCommentsByBookId() {
        var expectedBook = TestData.firstBook();
        when(commentRepository.findAllByBookId(expectedBook.getId())).thenReturn(TestData.firstBookComments());

        var actualComments = commentService.findAllByBookId(expectedBook.getId());

        assertThat(actualComments).containsExactlyInAnyOrderElementsOf(TestData.firstBookComments());
    }

    @Test
    void shouldReturnAllComments() {
        var expectedComments = TestData.allComments();
        when(commentRepository.findAll()).thenReturn(expectedComments);

        var actualComments = commentService.findAll();

        assertThat(actualComments).containsExactlyInAnyOrderElementsOf(expectedComments);
    }
}
