package otus.amogilevskiy.spring.service.author;

import otus.amogilevskiy.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAll();

    Optional<Author> findById(long id);

    Optional<Author> create(Author author);

    boolean deleteById(long id);

}
