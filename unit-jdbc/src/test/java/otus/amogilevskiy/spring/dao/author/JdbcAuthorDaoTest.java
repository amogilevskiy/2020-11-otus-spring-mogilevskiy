package otus.amogilevskiy.spring.dao.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcAuthorDao.class)
public class JdbcAuthorDaoTest {

    private static final int NON_EXISTING_AUTHOR_ID = 100500;

    @Autowired
    AuthorDao authorDao;

    @Test
    void shouldReturnAllAuthors() {
        var expectedAuthors = List.of(
                new Author(1L, "Test 1", "with", "middle name"),
                new Author(2L, "Test 2", "Only last name", null)
        );

        var actualAuthors = authorDao.findAll();

        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @Test
    void shouldReturnAuthorById() {
        var expectedAuthor = new Author(1L, "Test 1", "with", "middle name");

        var actualAuthor = authorDao.findById(expectedAuthor.getId());

        assertThat(actualAuthor).contains(expectedAuthor);
    }

    @Test
    void shouldReturnEmptyIfAuthorNotFoundById() {
        var actualAuthor = authorDao.findById(NON_EXISTING_AUTHOR_ID);

        assertThat(actualAuthor).isEmpty();
    }

    @Test
    void shouldCreateAuthor() {
        var expectedAuthor = new Author(null, "New author", "New last name", null);

        var id = authorDao.create(expectedAuthor);
        var actualAuthor = authorDao.findById(id.get());

        assertThat(actualAuthor.get()).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedAuthor);
    }

}
