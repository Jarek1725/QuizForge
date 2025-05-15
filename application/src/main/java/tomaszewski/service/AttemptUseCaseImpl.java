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
import java.util.Optional;
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
        Optional<AttemptModel> optionalAttemptModel = attemptRepositoryPort.findAttemptById(userSelectedAnswers.attemptId());
        if (optionalAttemptModel.isEmpty()) {
            throw new IllegalArgumentException("Attempt not found");
        }
        AttemptModel attemptModel = optionalAttemptModel.get();
        if (attemptModel.getScore() != -1) {
            throw new IllegalStateException("Attempt already submitted");
        }
        List<QuestionModel> allQuestionsByAttemptId = questionRepositoryPort.findAllQuestionsByAttemptId(userSelectedAnswers.attemptId());
        int score = 0;
        List<Long> userAnswersIds = new ArrayList<>();
        for (QuestionModel questionModel : allQuestionsByAttemptId) {
            List<Long> correctAnswers = new ArrayList<>();
            List<Long> userAnswers = new ArrayList<>();
            for (AnswerModel answer : questionModel.answers()) {
                if (answer.isCorrect()) {
                    correctAnswers.add(answer.id());
                }
                for (Long answerId : userSelectedAnswers.answerIds()) {
                    if (answer.id().equals(answerId)) {
                        userAnswers.add(answer.id());
                    }
                }
            }
            if (correctAnswers.containsAll(userAnswers) && userAnswers.containsAll(correctAnswers)) {
                score += questionModel.score();
            }
            userAnswersIds.addAll(userAnswers);
        }

        attemptModel.setScore(score);
        List<UserAnswersModel> userAnswersModels = userAnswerRepositoryPort.findAllByAttemptId(userSelectedAnswers.attemptId());
        for (UserAnswersModel userAnswersModel : userAnswersModels) {
            List<SelectedOptionModel> selectedOptionModels = new ArrayList<>();
            for (AnswerModel answer : userAnswersModel.getQuestion().answers()) {
                if (userAnswersIds.contains(answer.id())) {
                    selectedOptionModels.add(new SelectedOptionModel(
                            userAnswersModel.getId(),
                            answer.id()
                    ));
                }
            }
            userAnswersModel.setSelectedOptions(selectedOptionModels);
        }

        attemptRepositoryPort.save(attemptModel);
        userAnswerRepositoryPort.saveAll(userAnswersModels);
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