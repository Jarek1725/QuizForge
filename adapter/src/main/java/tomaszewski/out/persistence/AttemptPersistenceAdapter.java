package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.mapper.SelectedAnswerMapper;
import tomaszewski.model.AttemptModel;
import tomaszewski.out.entities.AttemptEntity;
import tomaszewski.port.out.AttemptRepositoryPort;
import tomaszewski.mapper.AttemptMapper;
import tomaszewski.out.repositories.JpaAttemptRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AttemptPersistenceAdapter implements AttemptRepositoryPort {
    private final JpaAttemptRepository jpaAttemptRepository;
    private final AttemptMapper attemptMapper;
    private final SelectedAnswerMapper selectedAnswerMapper;

    @Override
    public List<AttemptModel> findLastAttemptsByUser(Long userId) {
        return jpaAttemptRepository.findByUserIdOrderByStartTimeDesc(userId)
                .stream()
                .map(attemptMapper::toAttemptModel)
                .toList();
    }

    @Override
    public List<AttemptModel> findLastAttemptsByUserAndScoreMoreThan0(Long userId) {
        return jpaAttemptRepository.findByUserIdAndScoreGreaterThan(userId, -1)
                .stream()
                .map(attemptMapper::toAttemptModel)
                .toList();
    }

    @Override
    public AttemptModel save(AttemptModel attemptModel) {
        AttemptEntity saved = jpaAttemptRepository
                .save(attemptMapper.toAttemptEntity(attemptModel));
        return attemptMapper.toAttemptModel(saved);
    }

    @Override
    public Optional<AttemptModel> findAttemptById(Long attemptId) {
        return jpaAttemptRepository.findById(attemptId).map(attemptMapper::toAttemptModel);
    }
}
