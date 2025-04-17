package tomaszewski.usecase;

import tomaszewski.model.ExamModel;

import java.util.List;

public interface ExamUseCase {
    void createExam(ExamModel examModel, Long userId);
}
