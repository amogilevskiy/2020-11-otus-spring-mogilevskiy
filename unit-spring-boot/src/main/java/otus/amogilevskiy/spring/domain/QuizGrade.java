package otus.amogilevskiy.spring.domain;

public class QuizGrade {

    private final double score;

    private final boolean passed;

    public QuizGrade(double score, boolean passed) {
        this.score = score;
        this.passed = passed;
    }

    public static QuizGrade empty() {
        return new QuizGrade(0, false);
    }

    public double getScore() {
        return score;
    }

    public boolean isPassed() {
        return passed;
    }
}
