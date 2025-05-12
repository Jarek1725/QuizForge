package tomaszewski.model;

import java.util.List;

public class UserAnswersModel {

    private Long id;
    private Long userId;
    private Long answerId;
    private AttemptModel attempt;
    private QuestionModel question;
    private List<SelectedOptionModel> selectedOptions;

    public UserAnswersModel() {
    }

    public UserAnswersModel(Long userId, Long answerId, AttemptModel attempt, QuestionModel question) {
        this.userId = userId;
        this.answerId = answerId;
        this.attempt = attempt;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public AttemptModel getAttempt() {
        return attempt;
    }

    public void setAttempt(AttemptModel attempt) {
        this.attempt = attempt;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }

    public List<SelectedOptionModel> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<SelectedOptionModel> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
}
