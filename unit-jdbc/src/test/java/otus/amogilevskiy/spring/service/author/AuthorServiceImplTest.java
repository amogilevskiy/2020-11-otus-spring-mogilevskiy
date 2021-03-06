package otus.amogilevskiy.spring.service.author;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import otus.amogilevskiy.spring.dao.TestData;
import otus.amogilevskiy.spring.dao.author.AuthorDao;
import otus.amogilevskiy.spring.domain.Author;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@JdbcTest
@Import(AuthorServiceImpl.class)
public class AuthorServiceImplTest {

    @MockBean
    private AuthorDao authorDao;

    @Autowired
    private AuthorService authorService;

    private static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("first_name", "last_name", "middle_name"),
                Arguments.of("first_name", "last_name without middle_name", null)
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    void shouldReturnNewAuthor(String firstName, String lastName, String middleName) {
        var author = new Author(null, firstName, lastName, middleName);
        var expectedId = 1L;
        var expectedAuthor = new Author(expectedId, firstName, lastName, middleName);
        when(authorDao.create(author)).thenReturn(Optional.of(expectedId));
        when(authorDao.findById(expectedId)).thenReturn(Optional.of(expectedAuthor));

        var actualAuthor = authorService.create(author);

        assertThat(actualAuthor).contains(expectedAuthor);
    }

    @Test
    void shouldReturnAllAuthors() {
        var expectedAuthors = TestData.allAuthors();
        when(authorDao.findAll()).thenReturn(expectedAuthors);

        var actualAuthors = authorService.findAll();

        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

}
