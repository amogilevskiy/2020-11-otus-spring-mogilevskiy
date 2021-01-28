package otus.amogilevskiy.spring.dao.author;

import otus.amogilevskiy.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Optional<Author> findById(long id);

    List<Author> findAll();

    Optional<Long> create(Author author);

}
