package otus.amogilevskiy.spring.facade.book;

import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Genre;

public interface BookFacade {

    String create(Author author, Genre genre);

    String findBookById(long id);

    String findAllBooks();

    String updateBookById(long id);

    String deleteBookById(long id);

}
