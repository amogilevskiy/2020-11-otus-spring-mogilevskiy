package otus.amogilevskiy.spring.ui.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class AuthorViewImpl implements AuthorView {

    private final LocalizationService localizationService;

    @Override
    public String showAuthorDetailView(Author author) {
        var fullName = ObjectUtils.isEmpty(author.getMiddleName()) ? String.format("%s %s", author.getFirstName(), author.getLastName())
                : String.format("%s %s %s", author.getFirstName(), author.getMiddleName(), author.getLastName());
        return localizationService.localize("author.detail", new Object[]{author.getId(), fullName});
    }

}
