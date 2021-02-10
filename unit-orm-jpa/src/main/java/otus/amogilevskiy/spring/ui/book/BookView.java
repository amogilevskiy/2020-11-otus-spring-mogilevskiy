package otus.amogilevskiy.spring.ui.book;

import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.book.UpdateBookDto;

import java.util.Set;

public interface BookView {

    Book showCreateBookView(Set<Author> authors, Genre genre);

    UpdateBookDto showUpdateBookView(long id);

    String showBookDetailView(Book book);

}
