package tomaszewski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.in.AttemptUserCase;
import tomaszewski.model.AttemptModel;
import tomaszewski.out.AttemptRepositoryPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptService implements AttemptUserCase {
    private final AttemptRepositoryPort attemptRepositoryPort;

    @Override
    public List<AttemptModel> getLastAttempts(Long userId, int limit) {
        return List.of();
    }
}
