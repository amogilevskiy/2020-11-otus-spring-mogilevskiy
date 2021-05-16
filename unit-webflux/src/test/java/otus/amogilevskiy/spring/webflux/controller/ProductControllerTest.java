package otus.amogilevskiy.spring.webflux.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import otus.amogilevskiy.spring.webflux.domain.Category;
import otus.amogilevskiy.spring.webflux.domain.Product;
import otus.amogilevskiy.spring.webflux.dto.ProductDto;
import otus.amogilevskiy.spring.webflux.dto.ProductResponseDto;
import otus.amogilevskiy.spring.webflux.repository.CategoryRepository;
import otus.amogilevskiy.spring.webflux.repository.ProductRepository;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.*;

@SuppressWarnings("ReactiveStreamsUnusedPublisher")
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private WebTestClient client;

    @BeforeEach
    void setup() {
        client = WebTestClient
                .bindToController(productController)
                .build();
    }

    @Test
    void shouldReturnAllProducts() {
        client.get()
                .uri("/api/1.0/products")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(ProductResponseDto.class)
                .value(hasSize(3));
    }

    @Test
    @DirtiesContext
    void shouldReturnProductById() {
        var id = "1";

        var expectedProduct = Product.builder()
                .id(id)
                .title("title")
                .description("description")
                .build();

        productRepository.save(expectedProduct);

        client.get()
                .uri(String.format("/api/1.0/products/%s", id))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProductResponseDto.class)
                .value(ProductResponseDto::getId, equalTo(id))
                .value(ProductResponseDto::getTitle, equalTo(expectedProduct.getTitle()))
                .value(ProductResponseDto::getDescription, equalTo(expectedProduct.getDescription()));
    }

    @Test
    @DirtiesContext
    void shouldCreateNewProduct() {
        var dto = ProductDto.builder()
                .title("title")
                .description("description")
                .categoryId(categoryRepository.save(new Category("category")).getId())
                .build();
        client.post()
                .uri("/api/1.0/products")
                .body(Mono.just(dto), ProductDto.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(ProductResponseDto.class)
                .value(ProductResponseDto::getId, notNullValue())
                .value(ProductResponseDto::getTitle, equalTo(dto.getTitle()))
                .value(ProductResponseDto::getDescription, equalTo(dto.getDescription()));
    }

    @Test
    @DirtiesContext
    void shouldUpdateProductById() {
        var id = "1";

        var initialProduct = Product.builder()
                .id(id)
                .title("title")
                .description("description")
                .build();

        productRepository.save(initialProduct);

        var dto = ProductDto.builder()
                .title("new title")
                .description("new description")
                .build();

        client.patch()
                .uri(String.format("/api/1.0/products/%s", id))
                .body(Mono.just(dto), ProductDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProductResponseDto.class)
                .value(ProductResponseDto::getId, notNullValue())
                .value(ProductResponseDto::getTitle, equalTo(dto.getTitle()))
                .value(ProductResponseDto::getDescription, equalTo(dto.getDescription()));
    }

    @Test
    void shouldDeleteProductById() {
        var id = "should_be_deleted";

        var  product = Product.builder()
                .id(id)
                .title("title")
                .description("description")
                .build();

        productRepository.save(product);
        client.delete()
                .uri(String.format("/api/1.0/products/%s", id))
                .exchange()
                .expectStatus()
                .isNoContent();
    }

}
