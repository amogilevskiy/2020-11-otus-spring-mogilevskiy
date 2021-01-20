package otus.amogilevskiy.spring.service.auth;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.User;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private User currentUser;

    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    @Override
    public boolean login(User user) {
        if (currentUser == null) {
            currentUser = user;
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

}
