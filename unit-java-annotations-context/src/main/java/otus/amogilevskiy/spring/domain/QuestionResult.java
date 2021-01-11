package otus.amogilevskiy.spring.domain;

public class QuestionResult {

    private final Question question;

    private final String userAnswer;

    public QuestionResult(Question question, String userAnswer) {
        this.question = question;
        this.userAnswer = userAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getUserAnswer() {
        return userAnswer;
    }
}
