package otus.amogilevskiy.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.amogilevskiy.spring.facade.library.LibraryFacade;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommands {

    private final LibraryFacade libraryFacade;

    @ShellMethod(value = "Add a new book", key = {"ab", "add-book"})
    public String addBook() {
        return libraryFacade.addBook();
    }

    @ShellMethod(value = "Find an existing book by id", key = {"fb", "find-book"})
    public String findBook(@ShellOption long id) {
        return libraryFacade.findBookById(id);
    }

    @ShellMethod(value = "Find all existing books", key = {"fab", "find-all-books"})
    public String findAllBooks() {
        return libraryFacade.findAllBooks();
    }

    @ShellMethod(value = "Delete an existing book by id", key = {"db", "delete-book"})
    public String deleteBook(@ShellOption long id) {
        return libraryFacade.deleteBookById(id);
    }

    @ShellMethod(value = "Update an existing book by id", key = {"ub", "update-book"})
    public String updateBook(@ShellOption long id) {
        return libraryFacade.updateBookById(id);
    }

    @ShellMethod(value = "Find all comments by book id", key = {"fac", "find-all-comments"})
    public String findAllComments(@ShellOption long bookId) {
        return libraryFacade.showAllCommentsByBookId(bookId);
    }

    @ShellMethod(value = "Add a new comment to a book", key = {"ac", "add-comment"})
    public String addComment(@ShellOption long bookId, @ShellOption String text) {
        return libraryFacade.addComment(bookId, text);
    }

    @ShellMethod(value = "Delete an existing comment by id", key = {"dc", "delete-comment"})
    public String deleteComment(@ShellOption long id) {
        return libraryFacade.deleteCommentById(id);
    }

    @ShellMethod(value = "Update an existing comment by id", key = {"uc", "update-comment"})
    public String updateComment(@ShellOption long id, @ShellOption String text) {
        return libraryFacade.updateCommentById(id, text);
    }
}
