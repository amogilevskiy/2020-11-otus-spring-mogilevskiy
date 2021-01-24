package otus.amogilevskiy.spring.ui.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.book.UpdateBookDto;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class BookViewImpl implements BookView {

    private final FormService formService;
    private final LocalizationService localizationService;

    @Override
    public Book showCreateBookView(Author author, Genre genre) {
        formService.showLabelField(localizationService.localize("question.book.form"));
        var title = formService.showStringFormField(localizationService.localize("question.book.title"));
        return Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
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
        var author = book.getAuthor();
        var authorName = author.getMiddleName() == null ? String.format("%s %s", author.getFirstName(), author.getLastName())
                : String.format("%s %s %s", author.getFirstName(), author.getMiddleName(), author.getLastName());
        return localizationService.localize("book.detail", new Object[]{book.getId(), book.getTitle(), book.getGenre().getTitle(), authorName});
    }
}
