package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.in.mapper.QuestionMapper;
import tomaszewski.model.QuestionModel;
import tomaszewski.out.entities.QuestionEntity;
import tomaszewski.out.repositories.JpaQuestionRepository;
import tomaszewski.port.out.QuestionRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionPersistenceAdapter implements QuestionRepositoryPort {

    private final JpaQuestionRepository jpaQuestionRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionModel createQuestion(QuestionModel questionModel) {
        QuestionEntity questionEntity = questionMapper.toEntity(questionModel);

        if (questionEntity.getAnswers() != null) {
            questionEntity.getAnswers().forEach(answer -> answer.setQuestion(questionEntity));
        }

        QuestionEntity savedEntity = jpaQuestionRepository.save(questionEntity);

        return questionMapper.toModel(savedEntity);
    }

    @Override
    public List<QuestionModel> createQuestions(List<QuestionModel> questionModels) {
        return questionModels.stream()
                .map(this::createQuestion)
                .collect(Collectors.toList());
    }
}
