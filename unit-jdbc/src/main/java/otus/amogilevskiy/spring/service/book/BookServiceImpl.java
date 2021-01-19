package otus.amogilevskiy.spring.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dao.book.BookDao;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final FormService formService;
    private final BookDao bookDao;
    private final LocalizationService localizationService;

    @Override
    public Optional<Book> findBookById(long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Optional<Book> addBookUsingForm(long authorId, long genreId) {
        formService.showLabelField(localizationService.localize("question.book.form"));

        var book = createBookUsingForm(authorId, genreId);
        if (bookDao.findById(book.getId()).isPresent()) {
            formService.showLabelField(localizationService.localize("error.bookAlreadyExists"));
        } else if (bookDao.create(book)) {
            return Optional.of(book);
        }
        return Optional.empty();
    }

    @Override
    public boolean updateBookUsingForm(long id) {
        formService.showLabelField(localizationService.localize("question.book.updateForm"));

        return bookDao.findById(id).map(book -> {
            var title = formService.showStringFormField(localizationService.localize("question.book.title"));
            return bookDao.update(new Book(book.getId(), title, book.getAuthorId(), book.getGenreId()));
        }).orElse(false);
    }

    @Override
    public boolean deleteBookById(long id) {
        return bookDao.deleteById(id);
    }

    private Book createBookUsingForm(long authorId, long genreId) {
        var id = formService.showIntegerFormField(localizationService.localize("question.book.id"));
        var title = formService.showStringFormField(localizationService.localize("question.book.title"));
        return new Book(id, title, authorId, genreId);
    }

}
