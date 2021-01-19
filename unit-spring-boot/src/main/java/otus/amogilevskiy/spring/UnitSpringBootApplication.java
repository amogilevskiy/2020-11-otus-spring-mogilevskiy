package otus.amogilevskiy.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import otus.amogilevskiy.spring.config.AppProps;
import otus.amogilevskiy.spring.service.quiz.QuizService;

@EnableConfigurationProperties(AppProps.class)
@SpringBootApplication
public class UnitSpringBootApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(UnitSpringBootApplication.class, args);
        context.getBean(QuizService.class).startQuiz();
    }

}
