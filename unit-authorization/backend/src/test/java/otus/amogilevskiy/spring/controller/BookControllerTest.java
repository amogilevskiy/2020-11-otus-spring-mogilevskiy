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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import otus.amogilevskiy.spring.config.SecurityTestConfig;
import otus.amogilevskiy.spring.dto.book.BookDto;
import otus.amogilevskiy.spring.service.book.BookService;
import otus.amogilevskiy.spring.service.common.Resource;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;
import otus.amogilevskiy.spring.utils.TestData;

import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(ModelMapper.class)
@WithMockUser
@ContextConfiguration(classes = {SecurityTestConfig.class})
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() throws Exception {
        var books = TestData.allBooks();
        var page = new PageImpl<>(books);
        when(bookService.findAll(any())).thenReturn(page);

        mvc.perform(get("/api/1.0/books"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("content", hasSize(books.size())));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        var book = TestData.firstBook();

        when(bookService.findById(book.getId())).thenReturn(book);

        mvc.perform(get("/api/1.0/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(book.getId()), Long.class));
    }

    @Test
    void shouldCreateBook() throws Exception {
        var book = TestData.firstBook();

        var dto = BookDto.builder()
                .title(book.getTitle())
                .genreId(book.getGenre().getId())
                .authorIds(Set.of(TestData.FIRST_AUTHOR_ID))
                .build();

        var json = objectMapper.writeValueAsString(dto);

        when(bookService.create(dto)).thenReturn(book);

        mvc.perform(post("/api/1.0/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateBook() throws Exception {
        var book = TestData.firstBook();

        var dto = BookDto.builder()
                .title(book.getTitle())
                .genreId(book.getGenre().getId())
                .authorIds(Set.of(TestData.FIRST_AUTHOR_ID))
                .build();

        var json = objectMapper.writeValueAsString(dto);

        when(bookService.findById(book.getId())).thenReturn(book);
        when(bookService.updateById(book.getId(), dto)).thenReturn(book);

        mvc.perform(patch("/api/1.0/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteBook() throws Exception {
        var book = TestData.firstBook();

        when(bookService.deleteById(book.getId())).thenReturn(true);

        mvc.perform(delete("/api/1.0/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBookNotFoundError() throws Exception {
        when(bookService.findById(anyLong())).thenThrow(new ResourceNotFoundException(Resource.BOOK));

        mvc.perform(get("/api/1.0/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code", is("book_not_found")))
                .andExpect(jsonPath("message", is("Book not found.")))
                .andExpect(status().isNotFound());
    }

    @WithAnonymousUser
    @Test
    void shouldReturnUnAuthorizedError() throws Exception {
        mvc.perform(get("/api/1.0/books"))
                .andExpect(status().isUnauthorized());
    }

}
