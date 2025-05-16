package tomaszewski.usecase;

import tomaszewski.model.AttemptModel;
import tomaszewski.model.StartAttemptModel;
import tomaszewski.model.UserAnswersModel;
import tomaszewski.model.UserSelectedAnswers;

import java.util.List;

public interface AttemptUseCase {
    List<AttemptModel> getLastAttempts(Long userId);

    void submitAttempt(UserSelectedAnswers userSelectedAnswers);

    StartAttemptModel startAttempt(StartAttemptModel startAttemptModel);

    AttemptModel getAttemptById(Long attemptId);
}
