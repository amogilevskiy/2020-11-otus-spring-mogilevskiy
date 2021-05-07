package otus.amogilevskiy.spring.webflux.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import otus.amogilevskiy.spring.webflux.domain.Category;
import otus.amogilevskiy.spring.webflux.dto.CategoryDto;
import otus.amogilevskiy.spring.webflux.dto.CategoryResponseDto;
import otus.amogilevskiy.spring.webflux.repository.CategoryRepository;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private CategoryRepository categoryRepository;

    private WebTestClient client;

    @BeforeEach
    void setup() {
        client = WebTestClient
                .bindToController(categoryController)
                .build();
    }

    @Test
    void shouldReturnAllCategories() {
        client.get()
                .uri("/api/1.0/categories")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CategoryResponseDto.class)
                .value(hasSize(3));
    }

    @Test
    @DirtiesContext
    void shouldReturnCategoryById() {
        var id = "1";

        var expectedCategory = Category.builder()
                .id(id)
                .title("title")
                .build();

        categoryRepository.save(expectedCategory);

        client.get()
                .uri(String.format("/api/1.0/categories/%s", id))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CategoryResponseDto.class)
                .value(CategoryResponseDto::getId, equalTo(id))
                .value(CategoryResponseDto::getTitle, equalTo(expectedCategory.getTitle()));
    }

    @Test
    @DirtiesContext
    void shouldCreateNewCategory() {
        var dto = new CategoryDto("new category");

        client.post()
                .uri("/api/1.0/categories")
                .body(Mono.just(dto), CategoryDto.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(CategoryResponseDto.class)
                .value(CategoryResponseDto::getId, notNullValue())
                .value(CategoryResponseDto::getTitle, equalTo(dto.getTitle()));
    }

    @Test
    @DirtiesContext
    void shouldUpdateCategoryById() {
        var id = "1";

        var initialCategory = Category.builder()
                .id(id)
                .title("title")
                .build();

        categoryRepository.save(initialCategory);

        var dto = new CategoryDto("new title");

        client.patch()
                .uri(String.format("/api/1.0/categories/%s", id))
                .body(Mono.just(dto), CategoryDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CategoryResponseDto.class)
                .value(CategoryResponseDto::getId, notNullValue())
                .value(CategoryResponseDto::getTitle, equalTo(dto.getTitle()));
    }

    @Test
    void shouldDeleteCategoryById() {
        var id = "should_be_deleted";

        var category = Category.builder()
                .id(id)
                .title("title")
                .build();

        categoryRepository.save(category);
        client.delete()
                .uri(String.format("/api/1.0/categories/%s", id))
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
