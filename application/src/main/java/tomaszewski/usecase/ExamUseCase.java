package tomaszewski.usecase;

import tomaszewski.model.ExamModel;
import tomaszewski.model.ExamStatistics;

import java.util.List;

public interface ExamUseCase {
    Long createExam(ExamModel examModel, Long userId);

    List<ExamModel> getExams(String name, String category, String university, Integer limit);

    ExamModel getExamById(Long examId);

    ExamStatistics getStatsForUser(Long id, Long examId);
}
