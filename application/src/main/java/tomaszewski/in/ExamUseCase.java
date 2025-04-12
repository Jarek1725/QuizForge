package tomaszewski.in;

import tomaszewski.model.ExamModel;

import java.util.List;

public interface ExamUseCase {
    List<ExamModel> getLastExams(Long userId, int limit);
}
