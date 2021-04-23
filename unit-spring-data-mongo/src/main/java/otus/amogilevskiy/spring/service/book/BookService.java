package otus.amogilevskiy.spring.service.book;

import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findById(String id);

    Optional<Book> create(BookDto dto);

    boolean update(BookDto dto);

    List<Book> findAll();

    boolean deleteById(String id);

}
