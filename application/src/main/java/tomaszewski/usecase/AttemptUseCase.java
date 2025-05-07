package tomaszewski.usecase;

import tomaszewski.model.AttemptModel;
import tomaszewski.model.StartAttemptModel;
import tomaszewski.model.UserAnswersModel;

import java.util.List;

public interface AttemptUseCase {
    List<AttemptModel> getLastAttempts(Long userId, int limit);

    void submitAttempt(List<UserAnswersModel> userAnswerModels, Long attemptId);

    StartAttemptModel startAttempt(StartAttemptModel startAttemptModel);
}
