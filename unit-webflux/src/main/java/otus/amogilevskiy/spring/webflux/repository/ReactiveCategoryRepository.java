package otus.amogilevskiy.spring.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.amogilevskiy.spring.webflux.domain.Category;

public interface ReactiveCategoryRepository extends ReactiveMongoRepository<Category, String> {
}
