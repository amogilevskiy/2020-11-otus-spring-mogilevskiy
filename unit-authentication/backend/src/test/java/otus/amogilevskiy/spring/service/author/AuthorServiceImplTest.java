package otus.amogilevskiy.spring.service.author;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.dto.author.AuthorDto;
import otus.amogilevskiy.spring.repository.AuthorRepository;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@Import(AuthorServiceImpl.class)
public class AuthorServiceImplTest {

    @MockBean
    private AuthorRepository authorRepository;

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
    void shouldCreateNewAuthor(String firstName, String lastName, String middleName) {
        var dto = AuthorDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .build();

        var expectedAuthor = Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .build();

        when(authorRepository.save(expectedAuthor)).thenReturn(expectedAuthor);

        authorService.create(dto);

        verify(authorRepository).save(expectedAuthor);
    }

    @Test
    void shouldReturnAllAuthors() {
        var expectedAuthors = TestData.allAuthors();
        when(authorRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(expectedAuthors));

        var actualAuthors = authorService.findAll(PageRequest.of(0, 20));

        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @Test
    void shouldDeleteAuthorById() {
        var author = TestData.firstAuthor();

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        var actualResult = authorService.deleteById(author.getId());

        verify(authorRepository).deleteById(author.getId());
        assertThat(actualResult).isTrue();
    }

    @Test
    void shouldRaiseAuthorNotFoundException() {
        var id = 1L;

        var e = assertThrows(ResourceNotFoundException.class, () -> {
            authorService.findById(id);
        });

        assertThat(e.getMessage()).isEqualTo("author_not_found");
    }

}
