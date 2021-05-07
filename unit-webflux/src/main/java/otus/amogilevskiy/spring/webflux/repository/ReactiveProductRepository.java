package otus.amogilevskiy.spring.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.amogilevskiy.spring.webflux.domain.Product;

public interface ReactiveProductRepository extends ReactiveMongoRepository<Product, String> {
}
