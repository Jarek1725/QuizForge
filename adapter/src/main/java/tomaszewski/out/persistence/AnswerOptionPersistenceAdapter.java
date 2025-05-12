package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.mapper.AnswerOptionMapper;
import tomaszewski.model.AnswerOptionModel;
import tomaszewski.out.entities.AnswerOptionEntity;
import tomaszewski.out.repositories.JpaAnswerOptionRepository;
import tomaszewski.port.out.AnswerOptionRepositoryPort;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerOptionPersistenceAdapter implements AnswerOptionRepositoryPort {
    private final JpaAnswerOptionRepository jpaAnswerOptionRepository;
    private final AnswerOptionMapper answerOptionMapper;


    @Override
    public List<AnswerOptionModel> findAllByQuestionId(Long questionId) {
        List<AnswerOptionEntity> allByQuestionId = jpaAnswerOptionRepository.findAllByQuestionId(questionId);
        return answerOptionMapper.toAnswerOptionModels(allByQuestionId);

    }

    @Override
    public List<AnswerOptionModel> findAllByIdIn(List<Long> userAnswersIds) {
        List<AnswerOptionEntity> allByIdIn = jpaAnswerOptionRepository.findAllByIdIn(userAnswersIds);
        return answerOptionMapper.toAnswerOptionModels(allByIdIn);
    }
}
