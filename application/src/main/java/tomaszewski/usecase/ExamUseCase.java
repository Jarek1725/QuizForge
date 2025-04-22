package tomaszewski.usecase;

import tomaszewski.model.ExamModel;

import java.util.List;

public interface ExamUseCase {
    Long createExam(ExamModel examModel, Long userId);

    List<ExamModel> getExams(String category, String university, Integer limit);

    ExamModel getExamById(Long examId);
}
