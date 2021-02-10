package otus.amogilevskiy.spring.facade.book;

import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Genre;

import java.util.Set;

public interface BookFacade {

    String create(Set<Author> author, Genre genre);

    String findBookById(long id);

    String findAllBooks();

    String updateBookById(long id);

    String deleteBookById(long id);

}
