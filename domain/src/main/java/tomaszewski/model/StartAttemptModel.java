package tomaszewski.model;

import java.util.List;

public record StartAttemptModel(
        Long examId,
        Long userId,
        Long attemptId,
        List<QuestionModel> questions
) {
}
