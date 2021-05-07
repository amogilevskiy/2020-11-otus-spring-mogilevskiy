package otus.amogilevskiy.ecom.product;

import otus.amogilevskiy.ecom.product.domain.Category;
import otus.amogilevskiy.ecom.product.domain.Product;

import java.math.BigDecimal;

public class TestData {

    public static Category firstCategory() {
        return Category.builder()
                .id(1L)
                .title("Drinks")
                .build();
    }

    public static Product firstProduct() {
        return new Product(1L,
                "Milk",
                null,
                BigDecimal.valueOf(10.0),
                BigDecimal.valueOf(10.0),
                firstCategory()
        );
    }

}
