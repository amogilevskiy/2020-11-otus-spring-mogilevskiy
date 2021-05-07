package otus.amogilevskiy.spring.webflux.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.amogilevskiy.spring.webflux.domain.Category;
import otus.amogilevskiy.spring.webflux.dto.CategoryDto;
import otus.amogilevskiy.spring.webflux.dto.CategoryResponseDto;
import otus.amogilevskiy.spring.webflux.repository.ReactiveCategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(tags="Categories")
@RestController
@RequestMapping("/api/1.0/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ReactiveCategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public Flux<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().map(this::convertToResponseDto);
    }

    @GetMapping("/{id}")
    public Mono<CategoryResponseDto> findById(@PathVariable String id) {
        return categoryRepository.findById(id).map(this::convertToResponseDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CategoryResponseDto> create(@RequestBody CategoryDto dto) {
        return categoryRepository.save(convertToCategory(dto))
                .map(this::convertToResponseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable String id) {
        return categoryRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Mono<CategoryResponseDto> updateById(@PathVariable String id, @RequestBody CategoryDto dto) {
        return categoryRepository.findById(id)
                .flatMap(category -> {
                    if (dto.getTitle() != null) {
                        category.setTitle(dto.getTitle());
                    }
                    return categoryRepository.save(category);
                })
                .map(this::convertToResponseDto);
    }

    private CategoryResponseDto convertToResponseDto(Category category) {
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    private Category convertToCategory(CategoryDto dto) {
        return modelMapper.map(dto, Category.class);
    }

}
