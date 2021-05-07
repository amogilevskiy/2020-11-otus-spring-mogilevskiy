package otus.amogilevskiy.ecom.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otus.amogilevskiy.ecom.inventory.domain.ProductItem;
import otus.amogilevskiy.ecom.inventory.repository.ProductItemRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1.0/product-items")
public class ProductItemController {

    private final ProductItemRepository productItemRepository;

    @GetMapping("/{id}")
    public ProductItem findById(@PathVariable Long id) {
        return productItemRepository.findById(id).orElseThrow();
    }

    @GetMapping
    public List<ProductItem> findAll() {
        return productItemRepository.findAll();
    }

}
