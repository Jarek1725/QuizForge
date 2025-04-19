package tomaszewski.port.out;

import tomaszewski.model.ExamModel;

import java.util.List;
import java.util.Optional;

public interface ExamRepositoryPort {
    ExamModel save(ExamModel examModel);

    List<ExamModel> findExamsWithFilters(String category, String university, int effectiveLimit);

    Optional<ExamModel> findExamById(Long examId);
}
