package otus.amogilevskiy.spring.webflux.db;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import otus.amogilevskiy.spring.webflux.domain.Category;
import otus.amogilevskiy.spring.webflux.domain.Product;
import otus.amogilevskiy.spring.webflux.repository.CategoryRepository;
import otus.amogilevskiy.spring.webflux.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "categories", author = "amogilevskiy")
    public void categories(CategoryRepository categoryRepository) {
        categoryRepository.save(new Category("snacks"));
        categoryRepository.save(new Category("drinks"));
        categoryRepository.save(new Category("clothes"));
    }

    @ChangeSet(order = "002", id = "products_drinks", author = "amogilevskiy")
    public void drinksProducts(CategoryRepository categoryRepository, ProductRepository productRepository) {
        var category = categoryRepository.findFirstByTitle("drinks")
                .orElseThrow();

        var productTitles = List.of(
                "Best Juice",
                "Best Water",
                "Best Milk"
        );

        generateProducts(productTitles, category).forEach(productRepository::save);
    }

    private List<Product> generateProducts(List<String> titles, Category category) {
        var random = new Random();
        return titles.stream().map(title -> {
            var basePrice = random.nextInt(100000);
            var currentPrice = Math.round(basePrice * 0.9);
            return Product.builder()
                    .title(title)
                    .description(String.format("Description for %s", title))
                    .basePrice(BigDecimal.valueOf(basePrice, 2))
                    .currentPrice(BigDecimal.valueOf(currentPrice, 2))
                    .category(category)
                    .build();
        }).collect(Collectors.toList());
    }

}
