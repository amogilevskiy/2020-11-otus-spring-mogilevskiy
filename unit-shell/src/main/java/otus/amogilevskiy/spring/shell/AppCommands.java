package otus.amogilevskiy.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import otus.amogilevskiy.spring.service.auth.AuthService;
import otus.amogilevskiy.spring.service.quiz.QuizService;
import otus.amogilevskiy.spring.service.user.UserService;

@ShellComponent
@RequiredArgsConstructor
public class AppCommands {

    private final UserService userService;
    private final AuthService authService;
    private final QuizService quizService;

    @ShellMethod(value = "Start a new quiz", key = {"q", "quiz"})
    @ShellMethodAvailability(value = "isLoggedIn")
    public void startQuiz() {
        quizService.startQuiz();
    }

    @ShellMethod(value = "Login", key = {"li", "login"})
    @ShellMethodAvailability(value = "isGuest")
    public void login() {
        authService.login(userService.showUserForm());
    }

    @ShellMethod(value = "Logout", key = {"lo", "logout"})
    @ShellMethodAvailability(value = "isLoggedIn")
    public void logout() {
        authService.logout();
    }

    public Availability isLoggedIn() {
        return authService.isLoggedIn() ? Availability.available()
                : Availability.unavailable("You must be authenticated to start a quiz.");
    }

    public Availability isGuest() {
        return !authService.isLoggedIn() ? Availability.available()
                : Availability.unavailable("You must first logout.");
    }

}
