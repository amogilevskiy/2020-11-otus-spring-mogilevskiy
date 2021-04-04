package otus.amogilevskiy.spring.service.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.BookDto;

public interface BookService {

    Book findById(long id);

    Book create(BookDto dto);

    Book updateById(long id, BookDto dto);

    Page<Book> findAll(Pageable pageable);

    boolean deleteById(long id);

}
