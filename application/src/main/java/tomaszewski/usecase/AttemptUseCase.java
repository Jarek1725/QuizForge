package tomaszewski.usecase;

import tomaszewski.model.*;

import java.util.List;

public interface AttemptUseCase {
    List<AttemptModel> getLastAttempts(Long userId);

    void submitAttempt(UserSelectedAnswers userSelectedAnswers, boolean isExam);

    StartAttemptModel startAttempt(StartAttemptModel startAttemptModel);

    AttemptModel getAttemptById(Long attemptId);

    ProgressDataModel getAttemptProgressData(Long id);

    StartAttemptModel startReview(StartAttemptModel startAttemptModel);
}
