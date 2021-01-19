package otus.amogilevskiy.spring.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dao.author.AuthorDao;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.service.form.FormService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final FormService formService;
    private final LocalizationService localizationService;

    @Override
    public List<Author> findAllAuthors() {
        return authorDao.findAll();
    }

    @Override
    public Optional<Author> addAuthorUsingForm() {
        formService.showLabelField(localizationService.localize("question.author.form"));

        var author = createAuthorUsingForm();
        if (authorDao.findById(author.getId()).isPresent()) {
            formService.showLabelField(localizationService.localize("error.authorAlreadyExists"));
        } else if (authorDao.create(author)) {
            return Optional.of(author);
        }
        return Optional.empty();
    }

    private Author createAuthorUsingForm() {
        var id = formService.showIntegerFormField(localizationService.localize("question.author.id"));
        var firstName = formService.showStringFormField(localizationService.localize("question.author.firstName"));
        var lastName = formService.showStringFormField(localizationService.localize("question.author.lastName"));
        var middleName = formService.showStringFormField(localizationService.localize("question.author.middleName"));
        return new Author(id, firstName, lastName, middleName);
    }

}
