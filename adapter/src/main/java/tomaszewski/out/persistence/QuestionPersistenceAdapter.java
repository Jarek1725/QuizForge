package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.mapper.QuestionMapper;
import tomaszewski.model.ExamModel;
import tomaszewski.model.QuestionModel;
import tomaszewski.out.entities.ExamEntity;
import tomaszewski.out.entities.QuestionEntity;
import tomaszewski.out.repositories.JpaQuestionRepository;
import tomaszewski.port.out.QuestionRepositoryPort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionPersistenceAdapter implements QuestionRepositoryPort {

    private final JpaQuestionRepository jpaQuestionRepository;
    private final QuestionMapper questionMapper;
    private final ExamPersistenceAdapter examPersistenceAdapter;

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


    @Override
    public List<QuestionModel> getRandomQuestions(Long limit, Long examId) {
        Optional<ExamModel> examById = examPersistenceAdapter.findExamById(examId);
        if (examById.isEmpty()) {
            throw new IllegalArgumentException("Nie znaleziono egzaminu o podanym ID");
        }
        long effectiveLimit = (limit != null) ? limit : examById.get().questionsPerExam();
        List<QuestionEntity> randomQuestionsLimit = jpaQuestionRepository.findRandomQuestionsLimit(effectiveLimit, examId);
        return randomQuestionsLimit.stream()
                .map(questionMapper::toModel)
                .toList();
    }

    @Override
    public List<QuestionModel> findAllQuestionsByAttemptId(Long attemptId) {
        List<QuestionEntity> allQuestionsByAttemptId = jpaQuestionRepository.findAllQuestionsByAttemptId(attemptId);
        return questionMapper.toModels(allQuestionsByAttemptId);
    }

}