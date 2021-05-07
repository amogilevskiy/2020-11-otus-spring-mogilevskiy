package otus.amogilevskiy.ecom.product.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.amogilevskiy.ecom.product.client.InventoryServiceClient;
import otus.amogilevskiy.ecom.product.dto.ProductItemResponseDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceImplTest {

    @MockBean
    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    private ProductServiceImpl productService;

    @ParameterizedTest
    @CsvSource({"-1,confirmation required", "1,in stock"})
    public void shouldReturnCorrectQuantityValue(int quantity, String expectedValue) {
        var id = 1L;
        when(inventoryServiceClient.getProductItem(id))
                .thenReturn(new ProductItemResponseDto(id, quantity));

        var actualValue = productService.findById(id).getQuantity();

        assertThat(actualValue).isEqualTo(expectedValue);
    }

}
