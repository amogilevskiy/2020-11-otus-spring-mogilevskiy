package otus.amogilevskiy.spring.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.UpdateBookDto;
import otus.amogilevskiy.spring.repository.book.BookRepository;
import otus.amogilevskiy.spring.service.comment.CommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public boolean create(Book book) {
        return bookRepository.save(book).isPresent();
    }

    @Transactional
    @Override
    public boolean update(UpdateBookDto dto) {
        return bookRepository.updateTitleById(dto.getId(), dto.getTitle());
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        return commentService.deleteAllByBookId(id) && bookRepository.deleteById(id);
    }

}
