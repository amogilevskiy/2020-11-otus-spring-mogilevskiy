package otus.amogilevskiy.spring.service.author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.dto.author.AuthorDto;

import java.util.List;

public interface AuthorService {

    Page<Author> findAll(Pageable pageable);

    List<Author> findAllById(Iterable<Long> ids);

    Author findById(long id);

    Author create(AuthorDto dto);

    Author updateById(long id, AuthorDto dto);

    boolean deleteById(long id);

}
