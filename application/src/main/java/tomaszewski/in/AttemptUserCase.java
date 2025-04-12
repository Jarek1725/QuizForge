package tomaszewski.in;

import tomaszewski.model.AttemptModel;

import java.util.List;

public interface AttemptUserCase {
    List<AttemptModel> getLastAttempts(Long userId, int limit);
}
