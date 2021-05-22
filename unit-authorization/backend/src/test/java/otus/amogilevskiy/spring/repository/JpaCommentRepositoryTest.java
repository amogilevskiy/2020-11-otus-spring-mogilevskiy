package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpaCommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnCommentById() {
        var expectedComment = em.find(Comment.class, TestData.FIRST_COMMENT_ID);

        var actualComment = commentRepository.findById(TestData.FIRST_COMMENT_ID);

        assertThat(actualComment).isPresent().get().isEqualTo(expectedComment);
    }

    @Test
    void shouldReturnAllComments() {
        var actualComments = commentRepository.findAll();

        assertThat(actualComments).isNotNull().hasSize((int) TestData.NUMBER_OF_ALL_COMMENTS).allMatch(comment -> !comment.getText().isBlank());
    }

    @Test
    void shouldReturnAllCommentsByBookId() {
        var actualComments = commentRepository.findByBook_Id(1L, PageRequest.of(0, 20));

        assertThat(actualComments).containsExactlyInAnyOrderElementsOf(TestData.allCommentFromFirstBook());
    }

    @Test
    void shouldUpdateCommentById() {
        var id = TestData.FIRST_COMMENT_ID;

        var initialComment = em.find(Comment.class, id);
        var updatedText = "new text";

        assertThat(initialComment.getText()).isNotEqualTo(updatedText);

        initialComment.setText(updatedText);
        em.clear();
        commentRepository.save(initialComment);
        var actualComment = em.find(Comment.class, id);

        assertThat(actualComment.getText()).isEqualTo(updatedText);
    }

    @Test
    void shouldDeleteCommentById() {
        var comment = em.find(Comment.class, TestData.FIRST_COMMENT_ID);

        commentRepository.delete(comment);

        var actualComment = em.find(Comment.class, comment.getId());

        assertThat(actualComment).isNull();
    }

    @Test
    void shouldCreateNewComment() {
        var book = em.find(Book.class, TestData.firstBook().getId());
        var expectedComment = Comment.builder().text("new comment").book(book).build();
        commentRepository.save(expectedComment);

        var actualComment = em.find(Comment.class, TestData.NUMBER_OF_ALL_COMMENTS + 1);

        assertThat(actualComment).isNotNull().usingRecursiveComparison().ignoringFields("book", "id").isEqualTo(expectedComment);
    }

}
