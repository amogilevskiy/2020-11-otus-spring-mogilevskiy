package otus.amogilevskiy.spring.factory;

import otus.amogilevskiy.spring.domain.Answer;
import otus.amogilevskiy.spring.domain.Question;
import otus.amogilevskiy.spring.domain.QuestionResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionFactory {

    public List<QuestionResult> createQuestionResults(int count, double ratio) {
        var passedCount = count * ratio;
        return IntStream.range(0, count)
                .mapToObj(i -> createQuestionResult(i < passedCount))
                .collect(Collectors.toList());
    }

    private QuestionResult createQuestionResult(boolean passed) {
        var question = createQuestion();
        var iterator = question.getAnswers().listIterator();
        while (iterator.hasNext()) {
            var index = iterator.nextIndex();
            if (iterator.next().isCorrect() == passed) {
                return new QuestionResult(question, String.valueOf(index));
            }
        }
        throw new IllegalStateException("Question does not contain required answer.");
    }

    private Question createQuestion() {
        return new Question("test question", new ArrayList<>(Arrays.asList(
                new Answer("correct answer", true),
                new Answer("wrong answer", false)
        )));
    }

}
