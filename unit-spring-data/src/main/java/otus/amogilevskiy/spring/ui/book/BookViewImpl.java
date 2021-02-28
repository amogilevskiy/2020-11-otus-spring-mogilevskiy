package otus.amogilevskiy.spring.ui.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookViewImpl implements BookView {

    private final LocalizationService localizationService;

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
