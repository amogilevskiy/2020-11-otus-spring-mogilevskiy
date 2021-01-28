package otus.amogilevskiy.spring.facade.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.service.book.BookService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;
import otus.amogilevskiy.spring.ui.book.BookView;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookFacadeImpl implements BookFacade {

    private final LocalizationService localizationService;
    private final BookService bookService;
    private final BookView bookView;

    @Override
    public String create(Author author, Genre genre) {
        var book = bookView.showCreateBookView(author, genre);
        if (bookService.create(book)) {
            return localizationService.localize("success.bookAdded", new Object[]{book.getTitle()});
        } else {
            return localizationService.localize("error.bookNotAdded");
        }
    }

    @Override
    public String findBookById(long id) {
        return bookService.findById(id)
                .map(bookView::showBookDetailView)
                .orElse(localizationService.localize("error.bookNotFound"));
    }

    @Override
    public String findAllBooks() {
        return bookService.findAll()
                .stream()
                .map(bookView::showBookDetailView)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String updateBookById(long id) {
        var dto = bookView.showUpdateBookView(id);
        if (bookService.update(dto)) {
            return localizationService.localize("success.bookUpdated");
        }
        return localizationService.localize("error.bookNotUpdated");
    }

    @Override
    public String deleteBookById(long id) {
        if (bookService.deleteById(id)) {
            return localizationService.localize("success.bookDeleted");
        }
        return localizationService.localize("error.bookNotFound");
    }
}
