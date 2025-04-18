package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.model.AttemptModel;
import tomaszewski.port.out.AttemptRepositoryPort;
import tomaszewski.mapper.AttemptMapper;
import tomaszewski.out.repositories.JpaAttemptRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AttemptPersistenceAdapter implements AttemptRepositoryPort {
    private final JpaAttemptRepository jpaAttemptRepository;
    private final AttemptMapper attemptMapper;

    @Override
    public List<AttemptModel> findLastAttemptsByUser(Long userId, int limit) {
        return jpaAttemptRepository.findAttemptEntitiesByUserIdOrderByStartTimeDesc(1L)
                .stream()
                .map(attemptMapper::toAttemptModel)
                .toList();
    }
}
