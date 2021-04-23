package otus.amogilevskiy.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.amogilevskiy.spring.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {

    boolean existsByGenreId(String id);

    boolean existsByAuthorsId(String id);

}
