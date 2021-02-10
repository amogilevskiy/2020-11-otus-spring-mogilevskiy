package otus.amogilevskiy.spring.repository.book;

import otus.amogilevskiy.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(long id);

    List<Book> findAll();

    Optional<Book> save(Book book);

    boolean updateTitleById(long id, String title);

    boolean deleteById(long id);

}
