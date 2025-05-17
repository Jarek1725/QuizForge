package tomaszewski.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class AttemptModel {
    private Long id;
    private UserModel user;
    private ExamModel exam;
    private LocalDateTime startTime;
    private Integer score;
    private Boolean passed;
    private Boolean isAttemptExam;
    private List<UserAnswersModel> userAnswerModels;

    public AttemptModel() {
    }

    public AttemptModel(Long id, UserModel user, ExamModel exam, LocalDateTime startTime, Integer score, Boolean passed, List<UserAnswersModel> userAnswerModels) {
        this.id = id;
        this.user = user;
        this.exam = exam;
        this.startTime = startTime;
        this.score = score;
        this.passed = passed;
        this.userAnswerModels = userAnswerModels;
    }

    public AttemptModel(Long id, UserModel user, ExamModel exam, LocalDateTime startTime, Integer score, Boolean passed, Boolean isAttemptExam, List<UserAnswersModel> userAnswerModels) {
        this.id = id;
        this.user = user;
        this.exam = exam;
        this.startTime = startTime;
        this.score = score;
        this.passed = passed;
        this.isAttemptExam = isAttemptExam;
        this.userAnswerModels = userAnswerModels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setExam(ExamModel exam) {
        this.exam = exam;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public List<UserAnswersModel> getUserAnswerModels() {
        return userAnswerModels;
    }

    public void setUserAnswerModels(List<UserAnswersModel> userAnswerModels) {
        this.userAnswerModels = userAnswerModels;
    }

    public ExamModel getExam() {
        return exam;
    }

    public Boolean getAttemptExam() {
        return isAttemptExam;
    }

    public void setAttemptExam(Boolean attemptExam) {
        isAttemptExam = attemptExam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttemptModel)) return false;
        AttemptModel that = (AttemptModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(exam, that.exam) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(score, that.score) &&
                Objects.equals(passed, that.passed) &&
                Objects.equals(userAnswerModels, that.userAnswerModels);
    }
}