package tomaszewski.model;

import java.time.LocalDateTime;
import java.util.List;

public record AttemptModel(
        Long id,
        UserModel user,
        ExamModel exam,
        LocalDateTime startTime,
        Integer score,
        Boolean passed,
        List<UserAnswersModel> userAnswerModels
) {
}
