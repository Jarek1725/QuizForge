package tomaszewski.port.out;

import tomaszewski.model.AttemptModel;

import java.util.List;
import java.util.Optional;

public interface AttemptRepositoryPort {
    List<AttemptModel> findLastAttemptsByUser(Long userId, int limit);

    Long save(AttemptModel attemptModel);

    Optional<AttemptModel> findAttemptById(Long attemptId);
}
