package otus.amogilevskiy.spring.service.auth;

import otus.amogilevskiy.spring.domain.User;

import java.util.Optional;

public interface AuthService {

    boolean isLoggedIn();

    Optional<User> getCurrentUser();

    boolean login(User user);

    void logout();

}
