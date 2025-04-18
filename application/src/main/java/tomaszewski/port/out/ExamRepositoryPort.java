package tomaszewski.port.out;

import tomaszewski.model.ExamModel;

import java.util.List;

public interface ExamRepositoryPort {
    ExamModel save(ExamModel examModel);

    List<ExamModel> findExamsWithFilters(String category, String university, int effectiveLimit);
}
