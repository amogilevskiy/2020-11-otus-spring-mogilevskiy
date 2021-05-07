package otus.amogilevskiy.ecom.product.client;

import org.springframework.stereotype.Component;
import otus.amogilevskiy.ecom.product.dto.ProductItemResponseDto;

@Component
public class InventoryServiceClientFallback implements InventoryServiceClient {

    @Override
    public ProductItemResponseDto getProductItem(long id) {
        return new ProductItemResponseDto(id, ProductItemResponseDto.UNKNOWN_QUANTITY);
    }

}
