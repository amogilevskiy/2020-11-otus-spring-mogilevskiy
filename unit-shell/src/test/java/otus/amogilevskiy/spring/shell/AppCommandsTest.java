package otus.amogilevskiy.spring.shell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import otus.amogilevskiy.spring.service.auth.AuthService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AppCommandsTest {

    private static final String COMMAND_QUIZ = "quiz";
    private static final String COMMAND_LOGIN = "login";

    @Autowired
    private Shell shell;

    @MockBean
    private AuthService authService;

    @Test
    void shouldReturnUnAvailableWhenUserNotLoggedIn() {
        var result = shell.evaluate(() -> COMMAND_QUIZ);

        assertThat(result).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @Test
    void shouldReturnUnAvailableWhenUserLoggedIn() {
        when(authService.isLoggedIn()).thenReturn(true);

        var result = shell.evaluate(() -> COMMAND_LOGIN);

        assertThat(result).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

}
