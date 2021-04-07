package otus.amogilevskiy.spring.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;
import otus.amogilevskiy.spring.dto.comment.CommentResponseDto;
import otus.amogilevskiy.spring.service.comment.CommentService;

@RequiredArgsConstructor
@RequestMapping("/api/1.0/comments")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<CommentResponseDto> findAllByBookId(@RequestParam("book_id") long bookId, @PageableDefault(size = 30) Pageable pageable) {
        return commentService.findByBookId(bookId, pageable)
                .map(this::convertToResponseDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto create(@RequestBody CommentDto dto) {
        return convertToResponseDto(commentService.create(dto));
    }

    @GetMapping("/{id}")
    public CommentResponseDto findById(@PathVariable long id) {
        return convertToResponseDto(commentService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        commentService.deleteById(id);
    }

    private CommentResponseDto convertToResponseDto(Comment comment) {
        return modelMapper.map(comment, CommentResponseDto.class);
    }


}
