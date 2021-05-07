package otus.amogilevskiy.ecom.product.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import otus.amogilevskiy.ecom.product.dto.ProductItemResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InventoryServiceClientTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @Test
    void shouldReturnFallbackValue() {
        var id = 1L;
        var expectedResponse = new ProductItemResponseDto(id, ProductItemResponseDto.UNKNOWN_QUANTITY);

        var actualResponse = inventoryServiceClient.getProductItem(id);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

}
