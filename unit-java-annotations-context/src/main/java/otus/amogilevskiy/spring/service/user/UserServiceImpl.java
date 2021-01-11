package otus.amogilevskiy.spring.service.user;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.User;
import otus.amogilevskiy.spring.service.io.IOService;

@Service
public class UserServiceImpl implements UserService {

    private final IOService ioService;

    public UserServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public User showUserForm() {
        return new User(showFirstNameQuestion(), showLastNameQuestion());
    }

    private String showFirstNameQuestion() {
        return showUserFormQuestion("Please enter your First Name:");
    }

    private String showLastNameQuestion() {
        return showUserFormQuestion("Please enter your Last Name:");
    }

    private String showUserFormQuestion(String question) {
        ioService.out(question);
        return ioService.in();
    }
}
