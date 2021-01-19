package otus.amogilevskiy.spring.service.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.amogilevskiy.spring.dao.genre.GenreDao;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.service.form.FormService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GenreServiceImplTest {

    @MockBean
    GenreDao genreDao;

    @MockBean
    FormService formService;

    @Autowired
    GenreService genreService;

    @Test
    void shouldReturnNewGenre() {
        var expectedIntValue = 1L;
        var expectedStringValue = "Genre";
        var expectedGenre = new Genre(
                expectedIntValue,
                expectedStringValue
        );

        when(formService.showIntegerFormField(anyString())).thenReturn(expectedIntValue);
        when(formService.showStringFormField(anyString())).thenReturn(expectedStringValue);

        when(genreDao.findById(expectedIntValue)).thenReturn(Optional.empty());
        when(genreDao.create(any())).thenReturn(true);

        var actualGenre = genreService.addGenreUsingForm();

        assertThat(actualGenre).contains(expectedGenre);
    }

}
