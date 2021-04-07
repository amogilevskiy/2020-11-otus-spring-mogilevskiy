package otus.amogilevskiy.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import otus.amogilevskiy.spring.dto.author.AuthorDto;
import otus.amogilevskiy.spring.service.author.AuthorService;
import otus.amogilevskiy.spring.service.common.Resource;
import otus.amogilevskiy.spring.service.common.ResourceAlreadyExistsException;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;
import otus.amogilevskiy.spring.utils.TestData;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@Import(ModelMapper.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @Test
    void shouldReturnAllAuthors() throws Exception {
        var authors = TestData.allAuthors();
        var page = new PageImpl<>(authors);

        when(authorService.findAll(any())).thenReturn(page);

        mvc.perform(get("/api/1.0/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("content", hasSize(authors.size())));
    }

    @Test
    void shouldReturnAuthorById() throws Exception {
        var author = TestData.firstAuthor();

        when(authorService.findById(author.getId())).thenReturn(author);

        mvc.perform(get("/api/1.0/authors/" + author.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(author.getId()), Long.class));
    }

    @Test
    void shouldCreateAuthor() throws Exception {
        var author = TestData.firstAuthor();

        var dto = AuthorDto.builder()
                .firstName("First Name")
                .lastName("Last Name")
                .build();

        var json = objectMapper.writeValueAsString(dto);

        when(authorService.create(dto)).thenReturn(author);

        mvc.perform(post("/api/1.0/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateAuthor() throws Exception {
        var author = TestData.firstAuthor();

        var dto = AuthorDto.builder()
                .firstName("NEW FIRST NAME")
                .lastName(author.getLastName())
                .middleName(author.getMiddleName())
                .build();

        var json = objectMapper.writeValueAsString(dto);

        when(authorService.findById(author.getId())).thenReturn(author);
        when(authorService.updateById(author.getId(), dto)).thenReturn(author);

        mvc.perform(patch("/api/1.0/authors/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAuthor() throws Exception {
        var author = TestData.firstAuthor();

        when(authorService.deleteById(author.getId())).thenReturn(true);

        mvc.perform(delete("/api/1.0/authors/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAuthorNotFoundError() throws Exception {
        when(authorService.findById(anyLong())).thenThrow(new ResourceNotFoundException(Resource.AUTHOR));

        mvc.perform(get("/api/1.0/authors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code", is("author_not_found")))
                .andExpect(jsonPath("message", is("Author not found.")))
                .andExpect(status().isNotFound());
    }

}
