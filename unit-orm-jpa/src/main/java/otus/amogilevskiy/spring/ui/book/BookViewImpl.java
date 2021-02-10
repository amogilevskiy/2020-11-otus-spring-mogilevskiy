package otus.amogilevskiy.spring.ui.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.book.UpdateBookDto;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookViewImpl implements BookView {

    private final FormService formService;
    private final LocalizationService localizationService;

    @Override
    public Book showCreateBookView(Set<Author> authors, Genre genre) {
        formService.showLabelField(localizationService.localize("question.book.form"));
        var title = formService.showStringFormField(localizationService.localize("question.book.title"));
        return Book.builder()
                .title(title)
                .genre(genre)
                .authors(authors)
                .build();
    }

    @Override
    public UpdateBookDto showUpdateBookView(long id) {
        formService.showLabelField(localizationService.localize("question.book.updateForm"));
        var title = formService.showStringFormField(localizationService.localize("question.book.title"));
        return new UpdateBookDto(id, title);
    }

    @Override
    public String showBookDetailView(Book book) {
        var authors = book.getAuthors().stream()
                .map(author -> author.getMiddleName() == null
                        ? String.format("%s %s", author.getFirstName(), author.getLastName())
                        : String.format("%s %s %s", author.getFirstName(), author.getMiddleName(), author.getLastName()))
                .collect(Collectors.joining(", "));
        return localizationService.localize("book.detail", new Object[]{book.getId(), book.getTitle(), book.getGenre().getTitle(), authors});
    }
}
