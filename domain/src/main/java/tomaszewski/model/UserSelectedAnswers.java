package tomaszewski.model;

import java.util.List;

public record UserSelectedAnswers(
        List<Long> answerIds,
        Long attemptId,
        Long userId
) {
}
