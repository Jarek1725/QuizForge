package tomaszewski.model;

import java.time.LocalDateTime;

public record AttemptModel(
        Long id,
//        UserModel user,
        ExamModel exam,
        LocalDateTime attemptDate,
        Integer score,
        Boolean passed
) {
}
