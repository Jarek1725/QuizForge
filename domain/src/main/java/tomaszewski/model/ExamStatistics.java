package tomaszewski.model;

public class ExamStatistics {
    private final double averageScore;
    private final double lastScore;
    private final int attemptSum;
    private final double successRate;
    private final int questionPoolSize;

    public ExamStatistics(double averageScore, double lastScore, int attemptSum, double successRate, int questionPoolSize) {
        this.averageScore = averageScore;
        this.lastScore = lastScore;
        this.attemptSum = attemptSum;
        this.successRate = successRate;
        this.questionPoolSize = questionPoolSize;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public double getLastScore() {
        return lastScore;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public int getQuestionPoolSize() {
        return questionPoolSize;
    }

    public int getAttemptSum() {
        return attemptSum;
    }
}

