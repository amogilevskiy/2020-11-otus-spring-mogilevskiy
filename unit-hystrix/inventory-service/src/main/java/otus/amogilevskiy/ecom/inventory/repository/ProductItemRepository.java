package otus.amogilevskiy.ecom.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.ecom.inventory.domain.ProductItem;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
