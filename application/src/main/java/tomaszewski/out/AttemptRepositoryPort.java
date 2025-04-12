package tomaszewski.out;

import tomaszewski.model.AttemptModel;

import java.util.List;

public interface AttemptRepositoryPort {
    List<AttemptModel> findLastAttemptsByUser(Long userId, int limit);
}
