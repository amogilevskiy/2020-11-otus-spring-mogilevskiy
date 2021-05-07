package otus.amogilevskiy.spring.webflux.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.amogilevskiy.spring.webflux.domain.Product;
import otus.amogilevskiy.spring.webflux.dto.ProductDto;
import otus.amogilevskiy.spring.webflux.dto.ProductResponseDto;
import otus.amogilevskiy.spring.webflux.repository.ReactiveCategoryRepository;
import otus.amogilevskiy.spring.webflux.repository.ReactiveProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(tags="Products")
@RestController
@RequestMapping("/api/1.0/products")
@RequiredArgsConstructor
public class ProductController {

    private final ReactiveProductRepository productRepository;
    private final ReactiveCategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public Flux<ProductResponseDto> findAll() {
        return productRepository.findAll().map(this::convertToResponseDto);
    }

    @GetMapping("/{id}")
    public Mono<ProductResponseDto> findById(@PathVariable String id) {
        return productRepository.findById(id).map(this::convertToResponseDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponseDto> create(@RequestBody ProductDto dto) {
        return categoryRepository.findById(dto.getCategoryId())
                .flatMap(category -> {
                    var product = convertToProduct(dto);
                    product.setCategory(category);
                    return productRepository.save(product);
                }).map(this::convertToResponseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable String id) {
        return productRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Mono<ProductResponseDto> updateById(@PathVariable String id, @RequestBody ProductDto dto) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    if (dto.getTitle() != null) {
                        product.setTitle(dto.getTitle());
                    }

                    if (dto.getDescription() != null) {
                        product.setDescription(dto.getDescription());
                    }
                    return productRepository.save(product);
                })
                .map(this::convertToResponseDto);
    }

    private ProductResponseDto convertToResponseDto(Product product) {
        return modelMapper.map(product, ProductResponseDto.class);
    }

    private Product convertToProduct(ProductDto dto) {
        return modelMapper.map(dto, Product.class);
    }

}
