package otus.amogilevskiy.ecom.inventory;

import otus.amogilevskiy.ecom.inventory.domain.ProductItem;

import java.util.List;

public class TestData {

    public static List<ProductItem> allProductItems() {
        return List.of(
                firstProductItem(),
                new ProductItem(2L, 756757)
        );
    }

    public static ProductItem firstProductItem() {
        return new ProductItem(1L, 25263);
    }


}
