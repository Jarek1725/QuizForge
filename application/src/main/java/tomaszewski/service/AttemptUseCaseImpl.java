package tomaszewski.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.model.*;
import tomaszewski.port.out.*;
import tomaszewski.usecase.AttemptUseCase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttemptUseCaseImpl implements AttemptUseCase {
    private final AttemptRepositoryPort attemptRepositoryPort;
    private final ExamRepositoryPort examRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final QuestionRepositoryPort questionRepositoryPort;
    private final UserAnswerRepositoryPort userAnswerRepositoryPort;

    private static final int UNGRADED_ATTEMPT_SCORE = -1;

    @Override
    public List<AttemptModel> getLastAttempts(Long userId, int limit) {
        return attemptRepositoryPort.findLastAttemptsByUser(userId, limit);
    }

    @Override
    public void submitAttempt(UserSelectedAnswers userSelectedAnswers) {
        AttemptModel attemptModel = validateAndGetAttempt(userSelectedAnswers.attemptId());

        int score = calculateScore(userSelectedAnswers);
        attemptModel.setScore(score);

        updateUserAnswers(userSelectedAnswers);

        attemptRepositoryPort.save(attemptModel);
    }

    private AttemptModel validateAndGetAttempt(Long attemptId) {
        return attemptRepositoryPort.findAttemptById(attemptId)
                .map(attempt -> {
//                    if (attempt.getScore() != UNGRADED_ATTEMPT_SCORE) {
//                        throw new IllegalStateException("Attempt already submitted");
//                    }
                    return attempt;
                })
                .orElseThrow(() -> new IllegalArgumentException("Attempt not found"));
    }

    private int calculateScore(UserSelectedAnswers userSelectedAnswers) {
        List<QuestionModel> questions = questionRepositoryPort
                .findAllQuestionsByAttemptId(userSelectedAnswers.attemptId());

        int score = 0;
        for (QuestionModel question : questions) {
            List<Long> correctAnswerIds = getCorrectAnswerIds(question);
            List<Long> userAnswerIds = getUserAnswerIdsForQuestion(question, userSelectedAnswers.answerIds());

            if (areAnswersCorrect(correctAnswerIds, userAnswerIds)) {
                score++;
            }
        }

        return score;
    }

    private List<Long> getCorrectAnswerIds(QuestionModel question) {
        return question.answers().stream()
                .filter(AnswerModel::isCorrect)
                .map(AnswerModel::id)
                .collect(Collectors.toList());
    }

    private List<Long> getUserAnswerIdsForQuestion(QuestionModel question, List<Long> allUserAnswerIds) {
        List<Long> questionAnswerIds = question.answers().stream()
                .map(AnswerModel::id)
                .collect(Collectors.toList());

        return allUserAnswerIds.stream()
                .filter(questionAnswerIds::contains)
                .collect(Collectors.toList());
    }

    private boolean areAnswersCorrect(List<Long> correctAnswerIds, List<Long> userAnswerIds) {
        return correctAnswerIds.containsAll(userAnswerIds) &&
                userAnswerIds.containsAll(correctAnswerIds);
    }

    private void updateUserAnswers(UserSelectedAnswers userSelectedAnswers) {
        List<UserAnswersModel> userAnswersModels = userAnswerRepositoryPort
                .findAllByAttemptId(userSelectedAnswers.attemptId());

        Map<Long, List<Long>> questionToAnswerIdsMap = createQuestionToAnswerIdsMap(userAnswersModels);

        for (UserAnswersModel userAnswersModel : userAnswersModels) {
            Long questionId = userAnswersModel.getQuestion().id();
            List<Long> selectedAnswerIds = filterAnswerIdsForQuestion(
                    questionId, questionToAnswerIdsMap, userSelectedAnswers.answerIds());

            userAnswersModel.setSelectedOptions(
                    createSelectedOptionModels(userAnswersModel.getId(), selectedAnswerIds));
        }

        userAnswerRepositoryPort.saveAll(userAnswersModels);
    }

    private Map<Long, List<Long>> createQuestionToAnswerIdsMap(List<UserAnswersModel> userAnswersModels) {
        return userAnswersModels.stream()
                .collect(Collectors.toMap(
                        model -> model.getQuestion().id(),
                        model -> model.getQuestion().answers().stream()
                                .map(AnswerModel::id)
                                .collect(Collectors.toList())
                ));
    }

    private List<Long> filterAnswerIdsForQuestion(Long questionId,
                                                  Map<Long, List<Long>> questionToAnswerIdsMap,
                                                  List<Long> userAnswerIds) {
        List<Long> possibleAnswerIds = questionToAnswerIdsMap.getOrDefault(questionId, new ArrayList<>());
        return userAnswerIds.stream()
                .filter(possibleAnswerIds::contains)
                .collect(Collectors.toList());
    }

    private List<SelectedOptionModel> createSelectedOptionModels(Long userAnswerModelId, List<Long> selectedAnswerIds) {
        return selectedAnswerIds.stream()
                .map(answerId -> new SelectedOptionModel(userAnswerModelId, answerId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StartAttemptModel startAttempt(StartAttemptModel startAttemptModel) {
        ExamModel exam = getExamOrThrow(startAttemptModel.examId());
        UserModel user = getUserOrThrow(startAttemptModel.userId());

        List<QuestionModel> randomQuestions = getRandomQuestionsForExam(startAttemptModel.examId(), startAttemptModel.questionCount());

        AttemptModel attemptModel = createAndSaveAttempt(user, exam);

        createAndSaveUserAnswers(attemptModel, randomQuestions);

        return new StartAttemptModel(
                attemptModel.getUser().id(),
                attemptModel.getExam().id(),
                attemptModel.getId(),
                (long) randomQuestions.size(),
                randomQuestions
        );
    }

    private ExamModel getExamOrThrow(Long examId) {
        return examRepositoryPort.findExamById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exam ID"));
    }

    private UserModel getUserOrThrow(Long userId) {
        return userRepositoryPort.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    }

    private List<QuestionModel> getRandomQuestionsForExam(Long examId, Long questionCount) {
        List<QuestionModel> questions = questionRepositoryPort.getRandomQuestions(questionCount, examId);
        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions available");
        }
        return questions;
    }

    private AttemptModel createAndSaveAttempt(UserModel user, ExamModel exam) {
        AttemptModel attemptToSave = new AttemptModel(
                null,
                user,
                exam,
                LocalDateTime.now(),
                UNGRADED_ATTEMPT_SCORE,
                false,
                new ArrayList<>()
        );
        return attemptRepositoryPort.save(attemptToSave);
    }

    private void createAndSaveUserAnswers(AttemptModel attemptModel, List<QuestionModel> questions) {
        List<UserAnswersModel> userAnswersModels = questions.stream()
                .map(question -> new UserAnswersModel(null, null, attemptModel, question))
                .collect(Collectors.toList());

        userAnswerRepositoryPort.saveAll(userAnswersModels);
    }

    @Override
    public AttemptModel getAttemptById(Long attemptId) {
        return attemptRepositoryPort.findAttemptById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Attempt not found with ID: " + attemptId));
    }
}