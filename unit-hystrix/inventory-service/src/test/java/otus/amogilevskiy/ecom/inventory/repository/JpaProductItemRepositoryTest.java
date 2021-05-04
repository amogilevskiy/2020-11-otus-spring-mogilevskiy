package otus.amogilevskiy.ecom.inventory.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import otus.amogilevskiy.ecom.inventory.TestData;
import otus.amogilevskiy.ecom.inventory.domain.ProductItem;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpaProductItemRepositoryTest {

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnItemById() {
        var id = TestData.firstProductItem().getId();
        var expectedItem = em.find(ProductItem.class, id);

        var actualItem = productItemRepository.findById(id);

        assertThat(actualItem).isPresent().get().usingRecursiveComparison().isEqualTo(expectedItem);
    }
}
