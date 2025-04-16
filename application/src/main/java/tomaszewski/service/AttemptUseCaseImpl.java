package tomaszewski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.usecase.AttemptUseCase;
import tomaszewski.model.AttemptModel;
import tomaszewski.port.out.AttemptRepositoryPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptUseCaseImpl implements AttemptUseCase {
    private final AttemptRepositoryPort attemptRepositoryPort;

    @Override
    public List<AttemptModel> getLastAttempts(Long userId, int limit) {
        return attemptRepositoryPort.findLastAttemptsByUser(userId, limit);
    }
}
