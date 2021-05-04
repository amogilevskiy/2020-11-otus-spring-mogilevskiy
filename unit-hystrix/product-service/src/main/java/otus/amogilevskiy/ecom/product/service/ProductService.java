package otus.amogilevskiy.ecom.product.service;

import otus.amogilevskiy.ecom.product.dto.ProductResponseDto;

public interface ProductService {

    ProductResponseDto findById(long id);

}
