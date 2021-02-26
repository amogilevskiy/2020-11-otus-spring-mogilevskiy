package otus.amogilevskiy.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.amogilevskiy.spring.dto.book.BookDto;
import otus.amogilevskiy.spring.service.book.BookService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;
import otus.amogilevskiy.spring.ui.book.BookView;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BookService bookService;
    private final BookView bookView;
    private final LocalizationService localizationService;

    @ShellMethod(value = "Add a new book", key = {"ab", "add-book"})
    public String addBook(@ShellOption String title, @ShellOption long genreId, @ShellOption long authorId) {
        return bookService.create(BookDto.builder().title(title).genreId(genreId).authorId(authorId).build())
                .map(bookView::showBookDetailView)
                .orElse(localizationService.localize("error.bookNotAdded"));
    }

    @ShellMethod(value = "Find an existing book by id", key = {"fb", "find-book"})
    public String findBook(@ShellOption long id) {
        return bookService.findById(id).map(bookView::showBookDetailView).orElse(localizationService.localize("error.bookNotFound"));
    }

    @ShellMethod(value = "Find all existing books", key = {"fab", "find-all-books"})
    public String findAllBooks() {
        return bookService.findAll().stream().map(bookView::showBookDetailView).collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Delete an existing book by id", key = {"db", "delete-book"})
    public String deleteBook(@ShellOption long id) {
        if (bookService.deleteById(id)) {
            return localizationService.localize("success.bookDeleted");
        } else {
            return localizationService.localize("error.bookNotDeleted");
        }
    }

    @ShellMethod(value = "Update an existing book by id", key = {"ub", "update-book"})
    public String updateBook(@ShellOption long id, @ShellOption String title) {
        if (bookService.update(BookDto.builder().id(id).title(title).build())) {
            return localizationService.localize("success.bookUpdated");
        } else {
            return localizationService.localize("error.bookNotUpdated");
        }
    }

}
