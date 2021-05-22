package otus.amogilevskiy.spring.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.BookDto;
import otus.amogilevskiy.spring.dto.book.BookResponseDto;
import otus.amogilevskiy.spring.service.book.BookService;

@RestController
@RequestMapping(value = "/api/1.0/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<BookResponseDto> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return bookService.findAll(pageable)
                .map(this::convertToResponseDto);
    }

    @GetMapping("/{id}")
    public BookResponseDto findById(@PathVariable Long id) {
        return convertToResponseDto(bookService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto create(@RequestBody BookDto dto) {
        return convertToResponseDto(bookService.create(dto));
    }

    @PatchMapping("/{id}")
    public BookResponseDto updateById(@PathVariable("id") Long id, @RequestBody BookDto dto) {
        return convertToResponseDto(bookService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    private BookResponseDto convertToResponseDto(Book book) {
        return modelMapper.map(book, BookResponseDto.class);
    }
}