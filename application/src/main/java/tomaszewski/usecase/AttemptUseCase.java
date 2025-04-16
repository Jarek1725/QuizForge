package tomaszewski.usecase;

import tomaszewski.model.AttemptModel;

import java.util.List;

public interface AttemptUseCase {
    List<AttemptModel> getLastAttempts(Long userId, int limit);
}
