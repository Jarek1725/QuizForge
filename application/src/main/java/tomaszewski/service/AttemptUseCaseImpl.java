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

    @Override
    public List<AttemptModel> getLastAttempts(Long userId, int limit) {
        return attemptRepositoryPort.findLastAttemptsByUser(userId, limit);
    }

    @Override
    public void submitAttempt(List<UserAnswersModel> userAnswerModels, Long attemptId) {
        Optional<AttemptModel> optionalAttemptModel = attemptRepositoryPort.findAttemptById(attemptId);
        if (optionalAttemptModel.isEmpty()) {
            throw new IllegalArgumentException("Attempt not found");
        }
        AttemptModel attemptModel = optionalAttemptModel.get();
        if (attemptModel.score() != -1) {
            throw new IllegalStateException("Attempt already submitted");
        }

        AttemptModel updatedAttempt = new AttemptModel(
                attemptModel.id(),
                attemptModel.user(),
                attemptModel.exam(),
                attemptModel.startTime(),
                attemptModel.score(),
                true,
                userAnswerModels
        );

        attemptRepositoryPort.save(updatedAttempt);
    }

    @Override
    @Transactional
    public StartAttemptModel startAttempt(StartAttemptModel startAttemptModel) {
        Optional<ExamModel> examById = examRepositoryPort.findExamById(startAttemptModel.examId());
        Optional<UserModel> userById = userRepositoryPort.findUserById(startAttemptModel.userId());

        if (examById.isEmpty() || userById.isEmpty()) {
            throw new IllegalArgumentException("Invalid exam or user ID");
        }

        List<QuestionModel> randomQuestions = questionRepositoryPort.getRandomQuestions(3);
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
                savedAttemptModel.user().id(),
                savedAttemptModel.exam().id(),
                savedAttemptModel.id(),
                randomQuestions
        );
    }
}
