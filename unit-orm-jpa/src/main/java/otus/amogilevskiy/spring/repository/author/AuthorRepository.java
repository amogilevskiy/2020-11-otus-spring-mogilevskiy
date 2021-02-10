package otus.amogilevskiy.spring.repository.author;

import otus.amogilevskiy.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findById(long id);

    List<Author> findAll();

    Optional<Author> save(Author author);

}
