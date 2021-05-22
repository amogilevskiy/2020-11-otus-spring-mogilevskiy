package otus.amogilevskiy.spring.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.dto.author.AuthorDto;
import otus.amogilevskiy.spring.dto.author.AuthorResponseDto;
import otus.amogilevskiy.spring.service.author.AuthorService;

@RequiredArgsConstructor
@RequestMapping("/api/1.0/authors")
@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<AuthorResponseDto> findAll(@PageableDefault(size = 30) Pageable pageable) {
        return authorService.findAll(pageable)
                .map(this::convertToResponseDto);
    }

    @GetMapping("/{id}")
    public AuthorResponseDto findById(@PathVariable long id) {
        return convertToResponseDto(authorService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseDto create(@RequestBody AuthorDto dto) {
        return convertToResponseDto(authorService.create(dto));
    }

    @PatchMapping("/{id}")
    public AuthorResponseDto updateById(@PathVariable Long id, @RequestBody AuthorDto dto) {
        return convertToResponseDto(authorService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }

    private AuthorResponseDto convertToResponseDto(Author author) {
        return modelMapper.map(author, AuthorResponseDto.class);
    }

}
