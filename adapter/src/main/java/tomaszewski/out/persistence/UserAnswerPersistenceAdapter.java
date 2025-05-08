package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.mapper.UserAnswerMapper;
import tomaszewski.model.UserAnswersModel;
import tomaszewski.out.repositories.UserAnswerRepository;
import tomaszewski.port.out.UserAnswerRepositoryPort;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserAnswerPersistenceAdapter implements UserAnswerRepositoryPort {
    private final UserAnswerRepository userAnswerRepository;
    private final UserAnswerMapper userAnswerMapper;

    @Override
    public void saveAll(List<UserAnswersModel> userAnswersModels) {
        userAnswerRepository.saveAll(userAnswerMapper.toUserAnswerEntities(userAnswersModels));
    }

    @Override
    public List<UserAnswersModel> findAllByAttemptId(Long attemptId) {
        return userAnswerMapper.toUserAnswerModels(userAnswerRepository.findAllByAttemptId(attemptId));
    }
}
