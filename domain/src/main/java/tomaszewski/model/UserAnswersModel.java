package tomaszewski.model;

public record UserAnswersModel(
        Long userId,
        Long answerId,
        AttemptModel attempt
) {
}
