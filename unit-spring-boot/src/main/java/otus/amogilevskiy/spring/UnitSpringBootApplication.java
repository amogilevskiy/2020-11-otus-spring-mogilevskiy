package otus.amogilevskiy.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import otus.amogilevskiy.spring.service.quiz.QuizService;

@SpringBootApplication
public class UnitSpringBootApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(UnitSpringBootApplication.class, args);
		context.getBean(QuizService.class).startQuiz();
	}

}
