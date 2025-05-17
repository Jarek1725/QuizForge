package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.mapper.QuestionMapper;
import tomaszewski.model.ExamModel;
import tomaszewski.model.QuestionModel;
import tomaszewski.out.entities.ExamEntity;
import tomaszewski.out.entities.QuestionEntity;
import tomaszewski.out.entities.UserAnswerEntity;
import tomaszewski.out.repositories.JpaQuestionRepository;
import tomaszewski.out.repositories.UserAnswerRepository;
import tomaszewski.port.out.QuestionRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionPersistenceAdapter implements QuestionRepositoryPort {

    private final JpaQuestionRepository jpaQuestionRepository;
    private final QuestionMapper questionMapper;
    private final ExamPersistenceAdapter examPersistenceAdapter;
    private final UserAnswerRepository userAnswerRepository;

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

    @Override
    public List<QuestionModel> getRandomQuestionsForReview(Long examId, Long questionLimit, Long userId) {
        Optional<ExamModel> examById = examPersistenceAdapter.findExamById(examId);
        if (examById.isEmpty()) {
            throw new IllegalArgumentException("Nie znaleziono egzaminu o podanym ID");
        }
        long questionFullLimit = (questionLimit != null) ? questionLimit : examById.get().questionsPerExam();
        List<QuestionEntity> result = new ArrayList<>();
        List<QuestionEntity> newQuestions = jpaQuestionRepository.findNewRandomQuestionsLimit(questionFullLimit, examId, userId);
        if (!newQuestions.isEmpty()) {
            if (newQuestions.size() > questionFullLimit) {
                return newQuestions.stream()
                        .map(questionMapper::toModel)
                        .toList();
            }
            result.addAll(newQuestions);
        }

        List<UserAnswerEntity> incorrectAnswersByUserAndExam = userAnswerRepository.findIncorrectAnswersByUserAndExam(userId, examId);
        List<UserAnswerEntity> uniqueInCorrectAnswers = new ArrayList<>();
        if (!incorrectAnswersByUserAndExam.isEmpty()) {
            for (UserAnswerEntity userAnswerEntity : incorrectAnswersByUserAndExam) {
                List<Long> uniqueAnswersAttemptsIds = uniqueInCorrectAnswers.stream().map(e -> e.getQuestion().getId()).toList();
                if (!uniqueAnswersAttemptsIds.contains(userAnswerEntity.getQuestion().getId())) {
                    uniqueInCorrectAnswers.add(userAnswerEntity);
                }
            }
        }

        List<UserAnswerEntity> correctAnswersByUserAndExam = userAnswerRepository.findLatestCorrectAnswers(userId, examId);
        List<UserAnswerEntity> uniqueCorrectAnswers = new ArrayList<>();
        if (!correctAnswersByUserAndExam.isEmpty()) {
            for (UserAnswerEntity userAnswerEntity : correctAnswersByUserAndExam) {
                List<Long> uniqueAnswersAttemptsIds = uniqueCorrectAnswers.stream().map(e -> e.getQuestion().getId()).toList();
                if (!uniqueAnswersAttemptsIds.contains(userAnswerEntity.getQuestion().getId())) {
                    uniqueCorrectAnswers.add(userAnswerEntity);
                }
            }
        }

        List<Long> correctAnswerIds = correctAnswersByUserAndExam.stream().map(e -> e.getQuestion().getId()).toList();
        for (UserAnswerEntity uniqueInCorrectAnswer : uniqueInCorrectAnswers) {
            Long questionId = uniqueInCorrectAnswer.getQuestion().getId();
            if (correctAnswerIds.contains(questionId)) {
                System.out.println("TEST");
                Optional<UserAnswerEntity> first = uniqueCorrectAnswers.stream()
                        .filter(e -> e.getQuestion().getId().equals(questionId)).findFirst();
                if (first.isPresent()) {
                    UserAnswerEntity userAnswerEntity = first.get();
                    if (userAnswerEntity.getAttempt().getId() < uniqueInCorrectAnswer.getAttempt().getId()) {
                        QuestionEntity questionEntity = userAnswerEntity.getQuestion();
                        if (!result.contains(questionEntity)) {
                            result.add(questionEntity);
                        }
                    }
                }
            } else {
                result.add(uniqueInCorrectAnswer.getQuestion());
            }
        }

        if (result.size() < questionFullLimit) {
            List<QuestionEntity> randomQuestionsLimit = jpaQuestionRepository.findRandomQuestionsLimit(questionFullLimit - result.size(), examId);
            for (QuestionEntity questionEntity : randomQuestionsLimit) {
                if (!result.contains(questionEntity)) {
                    result.add(questionEntity);
                }
            }
        }

        return result.stream()
                .map(questionMapper::toModel)
                .toList();
    }

}