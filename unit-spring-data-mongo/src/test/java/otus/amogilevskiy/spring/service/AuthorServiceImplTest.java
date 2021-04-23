package otus.amogilevskiy.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.repository.AuthorRepository;
import otus.amogilevskiy.spring.service.author.AuthorServiceImpl;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    private static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("first_name", "last_name", "middle_name"),
                Arguments.of("first_name", "last_name without middle_name", null)
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    void shouldCreateNewAuthor(String firstName, String lastName, String middleName) {
        var expectedAuthor = Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .build();

        when(authorRepository.save(expectedAuthor)).thenReturn(expectedAuthor);

        authorService.create(expectedAuthor);

        verify(authorRepository).save(expectedAuthor);
    }

    @Test
    void shouldReturnAllAuthors() {
        var expectedAuthors = TestData.allAuthors();
        when(authorRepository.findAll()).thenReturn(expectedAuthors);

        var actualAuthors = authorService.findAll();

        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

}
