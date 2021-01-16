package otus.amogilevskiy.spring.service.user;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.User;
import otus.amogilevskiy.spring.service.io.IOService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
public class UserServiceImpl implements UserService {

    private final IOService ioService;

    private final LocalizationService localizationService;

    public UserServiceImpl(IOService ioService, LocalizationService localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public User showUserForm() {
        return new User(showFirstNameQuestion(), showLastNameQuestion());
    }

    private String showFirstNameQuestion() {
        return showUserFormQuestion(localizationService.localize("user.form.firstName"));
    }

    private String showLastNameQuestion() {
        return showUserFormQuestion(localizationService.localize("user.form.lastName"));
    }

    private String showUserFormQuestion(String question) {
        ioService.out(question);
        return ioService.in();
    }
}
