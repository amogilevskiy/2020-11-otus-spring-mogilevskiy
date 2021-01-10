package otus.amogilevskiy.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import otus.amogilevskiy.spring.service.QuizService;


@PropertySource("classpath:application.properties")
@ComponentScan
@Configuration
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();
        context.getBean(QuizService.class).startQuiz();
    }

}
