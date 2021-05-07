package otus.amogilevskiy.ecom.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otus.amogilevskiy.ecom.product.dto.ProductResponseDto;
import otus.amogilevskiy.ecom.product.service.ProductService;

@RestController
@RequestMapping("/api/1.0/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productService.findById(id);
    }

}
