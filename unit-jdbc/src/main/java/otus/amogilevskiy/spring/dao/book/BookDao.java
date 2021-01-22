package otus.amogilevskiy.spring.dao.book;

import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.service.book.BookDetail;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Optional<Book> findById(long id);

    Optional<BookDetail> findDetailById(long id);

    List<Book> findAll();

    List<Book> findAllByAuthorId(long authorId);

    boolean create(Book book);

    boolean update(Book book);

    boolean deleteById(long id);

}
