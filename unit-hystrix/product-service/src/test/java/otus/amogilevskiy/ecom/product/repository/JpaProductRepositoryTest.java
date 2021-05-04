package otus.amogilevskiy.ecom.product.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import otus.amogilevskiy.ecom.product.TestData;
import otus.amogilevskiy.ecom.product.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpaProductRepositoryTest {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnItemById() {
        var id = TestData.firstProduct().getId();
        var expectedProduct = em.find(Product.class, id);

        var actualProduct = productRepository.findById(id);

        assertThat(actualProduct).isPresent().get().usingRecursiveComparison().isEqualTo(expectedProduct);
    }

}
