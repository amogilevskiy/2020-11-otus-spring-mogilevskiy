package otus.amogilevskiy.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.amogilevskiy.spring.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
