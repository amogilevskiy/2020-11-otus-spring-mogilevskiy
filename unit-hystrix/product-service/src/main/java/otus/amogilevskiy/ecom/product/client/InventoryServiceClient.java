package otus.amogilevskiy.ecom.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import otus.amogilevskiy.ecom.product.dto.ProductItemResponseDto;

@FeignClient(value = "inventory-service/api/1.0", fallback = InventoryServiceClientFallback.class)
public interface InventoryServiceClient {

    @GetMapping("/product-items/{id}")
    ProductItemResponseDto getProductItem(@PathVariable("id") long id);

}
