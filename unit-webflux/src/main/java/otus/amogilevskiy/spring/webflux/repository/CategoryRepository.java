package otus.amogilevskiy.spring.webflux.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.amogilevskiy.spring.webflux.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findFirstByTitle(String title);

}
