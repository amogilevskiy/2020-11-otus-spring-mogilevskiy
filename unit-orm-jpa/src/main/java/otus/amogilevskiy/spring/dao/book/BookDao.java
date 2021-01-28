package otus.amogilevskiy.spring.dao.book;

import otus.amogilevskiy.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Optional<Book> findById(long id);

    List<Book> findAllByTitle(String title);

    List<Book> findAll();

    List<Book> findAllByAuthorId(long authorId);

    boolean create(Book book);

    boolean update(Book book);

    boolean deleteById(long id);

}
