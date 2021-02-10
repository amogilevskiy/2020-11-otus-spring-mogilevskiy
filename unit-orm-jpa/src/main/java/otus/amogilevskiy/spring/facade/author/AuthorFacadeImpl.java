package otus.amogilevskiy.spring.facade.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.service.author.AuthorService;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;
import otus.amogilevskiy.spring.ui.author.AuthorView;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorFacadeImpl implements AuthorFacade {

    private final FormService formService;
    private final LocalizationService localizationService;
    private final AuthorService authorService;
    private final AuthorView authorView;

    @Override
    public Set<Author> findOrCreateAuthors() {
        var authors = new HashSet<Author>();
        findOrCreateAuthor().ifPresent(authors::add);
        while (formService.showBooleanFormField(localizationService.localize("question.author.additional"))) {
            findOrCreateAuthor().ifPresent(authors::add);
        }
        return authors;
    }

    private Optional<Author> findOrCreateAuthor() {
        var shouldCreateAuthor = formService.showBooleanFormField(localizationService.localize("question.author.create"));
        if (shouldCreateAuthor) {
            return authorService.create(authorView.showCreateAuthorView());
        } else {
            var authors = authorService.findAll();
            var authorsString = authors.stream().map(Author::toString).collect(Collectors.joining("\n"));
            formService.showLabelField(authorsString);
            var authorId = formService.showIntegerFormField(localizationService.localize("question.author.existing"));
            return authors.stream().filter(author -> author.getId() == authorId).findAny();
        }
    }
}
