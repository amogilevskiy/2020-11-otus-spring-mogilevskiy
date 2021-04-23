package otus.amogilevskiy.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.amogilevskiy.spring.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
