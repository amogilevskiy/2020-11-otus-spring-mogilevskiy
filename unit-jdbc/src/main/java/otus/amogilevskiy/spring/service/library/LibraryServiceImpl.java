package otus.amogilevskiy.spring.service.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.service.author.AuthorService;
import otus.amogilevskiy.spring.service.book.BookService;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.genre.GenreService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final FormService formService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final LocalizationService localizationService;

    @Override
    public String findBookById(long id) {
        return bookService.findBookById(id)
                .map(Book::toString)
                .orElse(localizationService.localize("error.bookNotFound"));
    }

    @Override
    public String findAllBooks() {
        return bookService.findAllBooks()
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String addBook() {
        var author = findOrCreateAuthor();
        if (author.isEmpty()) {
            return localizationService.localize("error.authorNotAdded");
        } else {
            var genre = genreService.addGenreUsingForm();
            if (genre.isPresent()) {
                var addedBook = bookService.addBookUsingForm(author.get().getId(), genre.get().getId());
                return addedBook
                        .map(book ->
                                localizationService.localize("success.bookAdded", new Object[]{book}))
                        .orElse(localizationService.localize("error.bookNotAdded"));
            } else {
                return localizationService.localize("error.genreNotAdded");
            }
        }
    }

    private Optional<Author> findOrCreateAuthor() {
        var shouldCreateAuthor = formService.showBooleanFormField(localizationService.localize("question.author.create"));
        if (shouldCreateAuthor) {
            return authorService.addAuthorUsingForm();
        } else {
            var authors = authorService.findAllAuthors();
            var authorsString = authors.stream().map(Author::toString).collect(Collectors.joining("\n"));
            formService.showLabelField(authorsString);
            var authorId = formService.showIntegerFormField(localizationService.localize("question.author.existing"));
            return authors.stream().filter(author -> author.getId() == authorId).findAny();
        }
    }

    @Override
    public String updateBookById(long id) {
        if (bookService.updateBookUsingForm(id)) {
            return localizationService.localize("success.bookUpdated", new Object[]{id});
        }
        return localizationService.localize("error.bookNotUpdated");
    }

    @Override
    public String deleteBookById(long id) {
        if (bookService.deleteBookById(id)) {
            return localizationService.localize("success.bookDeleted", new Object[]{id});
        }
        return localizationService.localize("error.bookNotFound");
    }

}
