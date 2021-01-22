package otus.amogilevskiy.spring.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dao.author.AuthorDao;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.dto.author.CreateAuthorDto;
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

        var dto = createAuthorUsingForm();
        if (authorDao.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isPresent()) {
            formService.showLabelField(localizationService.localize("error.authorAlreadyExists"));
        } else if (authorDao.create(dto)) {
            return authorDao.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());
        }
        return Optional.empty();
    }

    private CreateAuthorDto createAuthorUsingForm() {
        var firstName = formService.showStringFormField(localizationService.localize("question.author.firstName"));
        var lastName = formService.showStringFormField(localizationService.localize("question.author.lastName"));
        var middleName = formService.showStringFormField(localizationService.localize("question.author.middleName"));
        return new CreateAuthorDto(firstName, lastName, middleName);
    }

}
