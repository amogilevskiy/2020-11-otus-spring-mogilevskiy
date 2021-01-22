package otus.amogilevskiy.spring.dao.author;

import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.dto.author.CreateAuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Optional<Author> findById(long id);

    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

    List<Author> findAll();

    boolean create(CreateAuthorDto dto);

    boolean deleteById(long id);

}
