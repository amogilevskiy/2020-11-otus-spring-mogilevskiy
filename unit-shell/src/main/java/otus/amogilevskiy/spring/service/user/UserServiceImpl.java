package otus.amogilevskiy.spring.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.User;
import otus.amogilevskiy.spring.service.io.IOService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final IOService ioService;
    private final LocalizationService localizationService;

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
