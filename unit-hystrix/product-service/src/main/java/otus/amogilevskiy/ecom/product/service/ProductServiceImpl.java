package otus.amogilevskiy.ecom.product.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.ecom.product.client.InventoryServiceClient;
import otus.amogilevskiy.ecom.product.dto.ProductItemResponseDto;
import otus.amogilevskiy.ecom.product.dto.ProductResponseDto;
import otus.amogilevskiy.ecom.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final InventoryServiceClient inventoryServiceClient;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponseDto findById(long id) {
        return productRepository.findById(id).map(product -> {
            var item = inventoryServiceClient.getProductItem(product.getId());
            var dto = modelMapper.map(product, ProductResponseDto.class);
            dto.setQuantity(createQuantityStringValue(item.getQuantity()));
            return dto;
        }).orElseThrow();
    }

    private String createQuantityStringValue(int quantity) {
        if (quantity == ProductItemResponseDto.UNKNOWN_QUANTITY) {
            return "confirmation required";
        }
        return "in stock";
    }
}
