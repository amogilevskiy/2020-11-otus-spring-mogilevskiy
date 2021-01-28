package otus.amogilevskiy.spring.service.book;

import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.UpdateBookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findById(long id);

    boolean create(Book book);

    boolean update(UpdateBookDto dto);

    List<Book> findAll();

    boolean deleteById(long id);

}
