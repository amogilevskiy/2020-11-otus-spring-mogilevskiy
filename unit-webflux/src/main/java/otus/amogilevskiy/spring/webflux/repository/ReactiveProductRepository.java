package otus.amogilevskiy.spring.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.amogilevskiy.spring.webflux.domain.Product;
import reactor.core.publisher.Flux;

public interface ReactiveProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findAllByCategoryId(String categoryId);

}
