package otus.amogilevskiy.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import otus.amogilevskiy.spring.service.QuizService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        context.getBean(QuizService.class).startQuiz();
    }

}
