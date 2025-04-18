package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.mapper.QuestionMapper;
import tomaszewski.model.QuestionModel;
import tomaszewski.out.entities.ExamEntity;
import tomaszewski.out.entities.QuestionEntity;
import tomaszewski.out.repositories.JpaQuestionRepository;
import tomaszewski.port.out.QuestionRepositoryPort;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionPersistenceAdapter implements QuestionRepositoryPort {

    private final JpaQuestionRepository jpaQuestionRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionModel createQuestion(QuestionModel questionModel, Long examId) {
        if (questionModel == null || examId == null) {
            throw new IllegalArgumentException("QuestionModel i examId nie mogą być null");
        }

        QuestionEntity questionEntity = questionMapper.toEntity(questionModel);

        ExamEntity examEntity = new ExamEntity();
        examEntity.setId(examId);
        questionEntity.setExam(examEntity);

        if (questionEntity.getAnswers() != null) {
            questionEntity.getAnswers().forEach(answer -> answer.setQuestion(questionEntity));
        }

        return questionMapper.toModel(jpaQuestionRepository.save(questionEntity));
    }

    @Override
    public List<QuestionModel> createQuestions(List<QuestionModel> questionModels, Long examId) {
        if (questionModels == null || examId == null) {
            throw new IllegalArgumentException("Lista pytań i examId nie mogą być null");
        }

        return questionModels.stream()
                .map(model -> createQuestion(model, examId))
                .toList();
    }
}