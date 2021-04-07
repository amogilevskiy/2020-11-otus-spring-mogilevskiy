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
import otus.amogilevskiy.spring.dto.genre.GenreDto;
import otus.amogilevskiy.spring.service.common.Resource;
import otus.amogilevskiy.spring.service.common.ResourceAlreadyExistsException;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;
import otus.amogilevskiy.spring.service.genre.GenreService;
import otus.amogilevskiy.spring.utils.TestData;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@Import(ModelMapper.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreService genreService;

    @Test
    void shouldReturnAllGenres() throws Exception {
        var genres = TestData.allGenres();
        var page = new PageImpl<>(genres);

        when(genreService.findAll(any())).thenReturn(page);

        mvc.perform(get("/api/1.0/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("content", hasSize(genres.size())));
    }

    @Test
    void shouldReturnGenreById() throws Exception {
        var genre = TestData.firstGenre();

        when(genreService.findById(genre.getId())).thenReturn(genre);

        mvc.perform(get("/api/1.0/genres/" + genre.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(genre.getId()), Long.class));
    }

    @Test
    void shouldCreateGenre() throws Exception {
        var genre = TestData.firstGenre();

        var dto = GenreDto.builder()
                .title(genre.getTitle())
                .build();

        var json = objectMapper.writeValueAsString(dto);

        when(genreService.create(dto)).thenReturn(genre);

        mvc.perform(post("/api/1.0/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnGenreNotFoundError() throws Exception {
        when(genreService.findById(anyLong())).thenThrow(new ResourceNotFoundException(Resource.GENRE));

        mvc.perform(get("/api/1.0/genres/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code", is("genre_not_found")))
                .andExpect(jsonPath("message", is("Genre not found.")))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnGenreAlreadyExistsError() throws Exception {
        when(genreService.findById(anyLong())).thenThrow(new ResourceAlreadyExistsException(Resource.GENRE));

        mvc.perform(get("/api/1.0/genres/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code", is("genre_already_exists")))
                .andExpect(jsonPath("message", is("Genre already exists.")))
                .andExpect(status().isConflict());
    }

}
