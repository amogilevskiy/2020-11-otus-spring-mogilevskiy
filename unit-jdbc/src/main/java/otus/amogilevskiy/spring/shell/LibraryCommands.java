package otus.amogilevskiy.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.amogilevskiy.spring.service.library.LibraryService;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommands {

    private final LibraryService libraryService;

    @ShellMethod(value = "Add a new book", key = {"ab", "add-book"})
    public String addBook() {
        return libraryService.addBook();
    }

    @ShellMethod(value = "Find an existing book by id", key = {"fb", "find-book"})
    public String findBook(@ShellOption long id) {
        return libraryService.findBookById(id);
    }

    @ShellMethod(value = "Find all existing books", key = {"fab", "find-all-books"})
    public String findAllBooks() {
        return libraryService.findAllBooks();
    }

    @ShellMethod(value = "Delete an existing book by id", key = {"db", "delete-book"})
    public String deleteBook(@ShellOption long id) {
        return libraryService.deleteBookById(id);
    }

    @ShellMethod(value = "Update an existing book by id", key = {"ub", "update-book"})
    public String updateBook(@ShellOption long id) {
        return libraryService.updateBookById(id);
    }

}
