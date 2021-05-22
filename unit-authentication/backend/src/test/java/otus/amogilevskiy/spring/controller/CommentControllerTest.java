package otus.amogilevskiy.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import otus.amogilevskiy.spring.config.SecurityTestConfig;
import otus.amogilevskiy.spring.dto.comment.CommentDto;
import otus.amogilevskiy.spring.service.comment.CommentService;
import otus.amogilevskiy.spring.service.common.Resource;
import otus.amogilevskiy.spring.service.common.ResourceAlreadyExistsException;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;
import otus.amogilevskiy.spring.utils.TestData;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@Import(ModelMapper.class)
@WithMockUser
@ContextConfiguration(classes = {SecurityTestConfig.class})
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Test
    void shouldReturnCommentsByBookId() throws Exception {
        var bookId = 1L;
        var comments = TestData.allCommentFromFirstBook();
        var page = new PageImpl<>(comments);
        var pageNumber = 0;
        var pageSize = 20;

        var request = PageRequest.of(pageNumber, pageSize);

        when(commentService.findByBookId(bookId, request)).thenReturn(page);

        mvc.perform(get("/api/1.0/comments")
                .queryParam("book_id", String.valueOf(bookId))
                .queryParam("page", String.valueOf(pageNumber))
                .queryParam("size", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("content", hasSize(comments.size())));
    }

    @Test
    void shouldReturnCommentById() throws Exception {
        var comment = TestData.firstComment();

        when(commentService.findById(comment.getId())).thenReturn(comment);

        mvc.perform(get("/api/1.0/comments/" + comment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(comment.getId()), Long.class));
    }

    @Test
    void shouldCreateComment() throws Exception {
        var comment = TestData.firstComment();

        var dto = CommentDto.builder()
                .text(comment.getText())
                .bookId(comment.getBook().getId())
                .build();

        var json = objectMapper.writeValueAsString(dto);

        when(commentService.create(dto)).thenReturn(comment);

        mvc.perform(post("/api/1.0/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnCommentNotFoundError() throws Exception {
        when(commentService.findById(anyLong())).thenThrow(new ResourceNotFoundException(Resource.COMMENT));

        mvc.perform(get("/api/1.0/comments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code", is("comment_not_found")))
                .andExpect(jsonPath("message", is("Comment not found.")))
                .andExpect(status().isNotFound());
    }

    @WithAnonymousUser
    @Test
    void shouldReturnUnAuthorizedError() throws Exception {
        mvc.perform(get("/api/1.0/comments"))
                .andExpect(status().isUnauthorized());
    }

}
