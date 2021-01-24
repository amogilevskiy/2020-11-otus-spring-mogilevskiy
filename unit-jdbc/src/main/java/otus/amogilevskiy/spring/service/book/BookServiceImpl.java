package otus.amogilevskiy.spring.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dao.book.BookDao;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.UpdateBookDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public Optional<Book> findById(long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public boolean create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public boolean update(UpdateBookDto dto) {
        return bookDao.findById(dto.getId()).map(book -> bookDao.update(Book.builder()
                .id(book.getId())
                .title(dto.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .build()))
                .orElse(false);
    }

    @Override
    public boolean deleteById(long id) {
        return bookDao.deleteById(id);
    }

}
