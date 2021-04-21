package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import otus.amogilevskiy.spring.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @ParameterizedTest
    @CsvSource({"1,2", "2, 1"})
    void shouldReturnAllCommentsByBookId(String bookId, long expectedCommentsCount) {
        var expectedComments = commentRepository.findAllByBookId(bookId);

        assertThat(expectedComments.size()).isEqualTo(expectedCommentsCount);
    }

    @Test
    void shouldReturnAllComments() {
        var actualComments = commentRepository.findAll();

        assertThat(actualComments).containsExactlyInAnyOrderElementsOf(TestData.allComments());
    }

}
