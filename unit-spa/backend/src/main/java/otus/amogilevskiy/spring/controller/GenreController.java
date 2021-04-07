package otus.amogilevskiy.spring.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.book.BookDto;
import otus.amogilevskiy.spring.dto.book.BookResponseDto;
import otus.amogilevskiy.spring.dto.genre.GenreDto;
import otus.amogilevskiy.spring.dto.genre.GenreResponseDto;
import otus.amogilevskiy.spring.service.genre.GenreService;

@RequiredArgsConstructor
@RequestMapping("/api/1.0/genres")
@RestController
public class GenreController {

    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<GenreResponseDto> findAll(@PageableDefault(size = 30) Pageable pageable) {
        return genreService.findAll(pageable)
                .map(this::convertToResponseDto);
    }

    @GetMapping("/{id}")
    public GenreResponseDto findById(@PathVariable long id) {
        return convertToResponseDto(genreService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreResponseDto create(@RequestBody GenreDto dto) {
        return convertToResponseDto(genreService.create(dto));
    }

    @PatchMapping("/{id}")
    public GenreResponseDto updateById(@PathVariable("id") Long id, @RequestBody GenreDto dto) {
        return convertToResponseDto(genreService.updateById(id, dto));
    }

    private GenreResponseDto convertToResponseDto(Genre genre) {
        return modelMapper.map(genre, GenreResponseDto.class);
    }

}
