package tomaszewski.model;

public class SelectedOptionModel {
    private Long userAnswerId;
    private Long answerOptionId;

    public SelectedOptionModel() {
    }

    public SelectedOptionModel(Long userAnswerId, Long answerOptionId) {
        this.userAnswerId = userAnswerId;
        this.answerOptionId = answerOptionId;
    }

    public Long getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(Long userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public Long getAnswerOptionId() {
        return answerOptionId;
    }

    public void setAnswerOptionId(Long answerOptionId) {
        this.answerOptionId = answerOptionId;
    }
}