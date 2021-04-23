package otus.amogilevskiy.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.service.author.AuthorService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;
import otus.amogilevskiy.spring.ui.author.AuthorView;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    private final AuthorService authorService;
    private final AuthorView authorView;
    private final LocalizationService localizationService;

    @ShellMethod(value = "Find all authors", key = {"faa", "find-all-authors"})
    public String findAllAuthors() {
        return authorService.findAll()
                .stream()
                .map(authorView::showAuthorDetailView)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Add a new author", key = {"aa", "add-author"})
    public String addAuthor(@ShellOption String firstName, @ShellOption String lastName, @ShellOption String middleName) {
        return authorService.create(Author.builder().firstName(firstName).lastName(lastName).middleName(middleName).build())
                .map(authorView::showAuthorDetailView)
                .orElse(localizationService.localize("error.authorNotAdded"));
    }

    @ShellMethod(value = "Delete an existing author by id", key = {"da", "delete-author"})
    public String deleteAuthor(@ShellOption String id) {
        if (authorService.deleteById(id)) {
            return localizationService.localize("success.authorDeleted");
        } else {
            return localizationService.localize("error.authorNotDeleted");
        }
    }
}
