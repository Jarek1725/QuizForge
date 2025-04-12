package tomaszewski.out;

import tomaszewski.model.ExamModel;

import java.util.List;

public interface ExamRepositoryPort {
    List<ExamModel> findLastExamsByUser(Long userId, int limit);
}
