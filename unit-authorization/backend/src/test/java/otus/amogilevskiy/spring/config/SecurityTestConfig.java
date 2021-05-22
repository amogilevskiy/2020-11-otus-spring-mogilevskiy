package otus.amogilevskiy.spring.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import otus.amogilevskiy.spring.config.security.ApiAuthenticationFailureHandler;
import otus.amogilevskiy.spring.config.security.ApiAuthenticationSuccessHandler;
import otus.amogilevskiy.spring.config.security.ApiLogoutSuccessHandler;
import otus.amogilevskiy.spring.config.security.UserAdapter;
import otus.amogilevskiy.spring.domain.User;

import java.util.List;

@TestConfiguration
@Import({ApiAuthenticationSuccessHandler.class, ApiLogoutSuccessHandler.class, ApiAuthenticationFailureHandler.class})
public class SecurityTestConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> new UserAdapter(new User(1L, username, "password", true, List.of()));
    }

}
