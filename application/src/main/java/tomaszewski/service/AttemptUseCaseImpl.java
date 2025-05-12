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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttemptUseCaseImpl implements AttemptUseCase {
    private final AttemptRepositoryPort attemptRepositoryPort;
    private final ExamRepositoryPort examRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final QuestionRepositoryPort questionRepositoryPort;
    private final UserAnswerRepositoryPort userAnswerRepositoryPort;
    private final AnswerOptionRepositoryPort answerOptionRepositoryPort;

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
                score++;
            }
            userAnswersIds.addAll(userAnswers);
        }

        attemptModel.setScore(score);
        List<AnswerOptionModel> selectedAnswers = answerOptionRepositoryPort.findAllByIdIn(userAnswersIds);
        List<UserAnswersModel> userAnswersModels = userAnswerRepositoryPort.findAllByAttemptId(userSelectedAnswers.attemptId());

        for (UserAnswersModel userAnswersModel : userAnswersModels) {
            List<SelectedOptionModel> selectedOptionModels = new ArrayList<>();
            for (AnswerModel answer : userAnswersModel.getQuestion().answers()) {
                if(userAnswersIds.contains(answer.id())) {
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
        Optional<ExamModel> examById = examRepositoryPort.findExamById(startAttemptModel.examId());
        Optional<UserModel> userById = userRepositoryPort.findUserById(startAttemptModel.userId());

        if (examById.isEmpty() || userById.isEmpty()) {
            throw new IllegalArgumentException("Invalid exam or user ID");
        }

        List<QuestionModel> randomQuestions = questionRepositoryPort.getRandomQuestions(3, startAttemptModel.examId());
        if (randomQuestions.isEmpty()) {
            throw new IllegalStateException("No questions available");
        }

        AttemptModel attemptToSave = new AttemptModel(
                null,
                userById.get(),
                examById.get(),
                LocalDateTime.now(),
                -1,
                false,
                new ArrayList<>()
        );
        AttemptModel savedAttemptModel = attemptRepositoryPort.save(attemptToSave);

        List<UserAnswersModel> userAnswersModels = new ArrayList<>();
        for (QuestionModel question : randomQuestions) {
            userAnswersModels.add(new UserAnswersModel(
                    null,
                    null,
                    savedAttemptModel,
                    question
            ));
        }

        userAnswerRepositoryPort.saveAll(userAnswersModels);

        return new StartAttemptModel(
                savedAttemptModel.getUser().id(),
                savedAttemptModel.getExam().id(),
                savedAttemptModel.getId(),
                randomQuestions
        );
    }
}
