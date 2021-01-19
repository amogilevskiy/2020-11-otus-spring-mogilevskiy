package otus.amogilevskiy.spring.service.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.amogilevskiy.spring.dao.author.AuthorDao;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.service.form.FormService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    void shouldReturnNewAuthor() {
        var expectedIntValue = 1L;
        var expectedStringValue = "Name";
        var expectedAuthor = new Author(
                expectedIntValue,
                expectedStringValue,
                expectedStringValue,
                expectedStringValue
        );

        when(formService.showIntegerFormField(anyString())).thenReturn(expectedIntValue);
        when(formService.showStringFormField(anyString())).thenReturn(expectedStringValue);

        when(authorDao.findById(expectedIntValue)).thenReturn(Optional.empty());
        when(authorDao.create(any())).thenReturn(true);

        var actualAuthor = authorService.addAuthorUsingForm();

        assertThat(actualAuthor).contains(expectedAuthor);
    }

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
