package otus.amogilevskiy.spring.webflux.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.amogilevskiy.spring.webflux.domain.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
