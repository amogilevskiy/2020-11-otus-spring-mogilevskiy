package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.repository.comment.CommentRepository;
import otus.amogilevskiy.spring.repository.comment.JpaCommentRepository;
import otus.amogilevskiy.spring.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaCommentRepository.class)
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
    void shouldReturnCommentsByBookId() {
        var firstComment = em.find(Comment.class, TestData.FIRST_COMMENT_ID);
        var secondComment = em.find(Comment.class, TestData.SECOND_COMMENT_ID);

        var actualComments = commentRepository.findAllByBookId(TestData.firstBook().getId());

        assertThat(actualComments).containsExactlyInAnyOrder(firstComment, secondComment);
    }

    @Test
    void shouldReturnAllComments() {
        var actualComments = commentRepository.findAll();

        assertThat(actualComments).isNotNull().hasSize((int) TestData.NUMBER_OF_ALL_COMMENTS).allMatch(comment -> !comment.getText().isBlank());
    }

    @Test
    void shouldUpdateCommentById() {
        var id = TestData.FIRST_COMMENT_ID;

        var initialComment = em.find(Comment.class, id);
        var updatedText = "new text";

        assertThat(initialComment.getText()).isNotEqualTo(updatedText);
        em.clear();
        commentRepository.updateTextById(id, updatedText);
        var actualComment = em.find(Comment.class, id);

        assertThat(actualComment.getText()).isEqualTo(updatedText);
    }

    @Test
    void shouldDeleteCommentById() {
        var comment = em.find(Comment.class, TestData.FIRST_COMMENT_ID);

        em.clear();
        commentRepository.deleteById(comment.getId());

        var actualResult = em.find(Comment.class, comment.getId());

        assertThat(actualResult).isNull();
    }

    @Test
    void shouldDeleteAllCommentsByBookId() {
        var bookId = 1L;
        var query = em.getEntityManager().createQuery("select c from Comment c where c.book.id = :book_id", Comment.class);
        query.setParameter("book_id", bookId);
        var initialComments = query.getResultList();

        assertThat(initialComments).hasSizeGreaterThan(0);

        var actualResult = commentRepository.deleteAllByBookId(bookId);

        var actualComments = query.getResultList();
        
        assertThat(actualResult).isTrue();
        assertThat(actualComments).hasSize(0);
    }

    @Test
    void shouldCreateNewComment() {
        var expectedComment = new Comment(null, "new comment", new Book(TestData.firstBook().getId()));
        commentRepository.save(expectedComment);

        var actualComment = em.find(Comment.class, TestData.NUMBER_OF_ALL_COMMENTS + 1);

        assertThat(actualComment).isNotNull().usingRecursiveComparison().ignoringFields("book", "id").isEqualTo(expectedComment);
    }

}
