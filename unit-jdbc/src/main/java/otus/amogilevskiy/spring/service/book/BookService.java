package otus.amogilevskiy.spring.service.book;

import otus.amogilevskiy.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findBookById(long id);

    List<Book> findAllBooks();

    Optional<Book> addBookUsingForm(long authorId, long genreId);

    boolean updateBookUsingForm(long id);

    boolean deleteBookById(long id);

}
