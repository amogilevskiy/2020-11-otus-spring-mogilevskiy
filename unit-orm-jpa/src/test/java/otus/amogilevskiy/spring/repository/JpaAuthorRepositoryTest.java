package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.utils.TestData;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.repository.author.AuthorRepository;
import otus.amogilevskiy.spring.repository.author.JpaAuthorRepository;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuthorRepository.class)
public class JpaAuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnAuthorById() {
        var expectedAuthor = em.find(Author.class, TestData.FIRST_AUTHOR_ID);

        var actualAuthor = authorRepository.findById(TestData.FIRST_AUTHOR_ID);

        assertThat(actualAuthor).isPresent().get().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldReturnAllAuthors() {
        var firstAuthor = em.find(Author.class, TestData.FIRST_AUTHOR_ID);
        var secondAuthor = em.find(Author.class, TestData.SECOND_AUTHOR_ID);

        var actualAuthors = authorRepository.findAll();

        assertThat(actualAuthors).containsExactlyInAnyOrder(firstAuthor, secondAuthor);
    }

    @Test
    void shouldCreateNewAuthor() {
        var expectedAuthor = new Author(null, "first name", "last name", "middle name", Set.of());
        authorRepository.save(expectedAuthor);

        var actualAuthor = em.find(Author.class, TestData.NUMBER_OF_ALL_AUTHORS + 1L);

        assertThat(actualAuthor).isNotNull().usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedAuthor);
    }

}
