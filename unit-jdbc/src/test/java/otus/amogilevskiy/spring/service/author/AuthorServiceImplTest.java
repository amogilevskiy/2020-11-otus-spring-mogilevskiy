package otus.amogilevskiy.spring.service.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.amogilevskiy.spring.dao.author.AuthorDao;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.service.form.FormService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceImplTest {

    @MockBean
    FormService formService;

    @MockBean
    AuthorDao authorDao;

    @Autowired
    AuthorService authorService;

    @Test
    void shouldReturnAllAuthors() {
        var expectedAuthors = List.of(
                new Author(1, "Name", "Last name", "Middle name"),
                new Author(2, "Name", "Last name", "Middle name")
        );

        when(authorDao.findAll()).thenReturn(expectedAuthors);

        var actualAuthors = authorService.findAllAuthors();

        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(actualAuthors);
    }

}
