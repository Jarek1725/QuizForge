package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.model.AttemptModel;
import tomaszewski.out.AttemptRepositoryPort;
import tomaszewski.out.ExamRepositoryPort;
import tomaszewski.out.repositories.JpaAttemptRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AttemptPersistenceAdapter implements AttemptRepositoryPort {
    private final JpaAttemptRepository jpaAttemptRepository;

    @Override
    public List<AttemptModel> findLastAttemptsByUser(Long userId, int limit) {
        return List.of();
    }
}
