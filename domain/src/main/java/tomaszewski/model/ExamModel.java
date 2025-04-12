package tomaszewski.model;

import java.time.LocalDate;
import java.util.List;

public record ExamModel(
        Long id,
        String name,
        LocalDate examDate,
        Integer questionsPerAttempt,
        Long universityId,
        List<Long> categoryIds
) {
}
