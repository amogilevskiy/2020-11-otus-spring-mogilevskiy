package otus.amogilevskiy.spring.ui.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class AuthorViewImpl implements AuthorView {

    private final FormService formService;
    private final LocalizationService localizationService;

    @Override
    public Author showCreateAuthorView() {
        formService.showLabelField(localizationService.localize("question.author.form"));
        var firstName = formService.showStringFormField(localizationService.localize("question.author.firstName"));
        var lastName = formService.showStringFormField(localizationService.localize("question.author.lastName"));
        var middleName = formService.showStringFormField(localizationService.localize("question.author.middleName"));
        return Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .build();
    }

}
