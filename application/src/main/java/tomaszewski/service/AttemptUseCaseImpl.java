package tomaszewski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.model.*;
import tomaszewski.port.out.ExamRepositoryPort;
import tomaszewski.port.out.QuestionRepositoryPort;
import tomaszewski.port.out.UserRepositoryPort;
import tomaszewski.usecase.AttemptUseCase;
import tomaszewski.port.out.AttemptRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttemptUseCaseImpl implements AttemptUseCase {
    private final AttemptRepositoryPort attemptRepositoryPort;
    private final ExamRepositoryPort examRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final QuestionRepositoryPort questionRepositoryPort;

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
    public StartAttemptModel startAttempt(StartAttemptModel startAttemptModel) {
        Optional<ExamModel> examById = examRepositoryPort.findExamById(startAttemptModel.examId());
        Optional<UserModel> userById = userRepositoryPort.findUserById(startAttemptModel.userId());

        if (examById.isPresent() && userById.isPresent()) {
            AttemptModel attemptModel = new AttemptModel(
                    null,
                    userById.get(),
                    examById.get(),
                    LocalDateTime.now(),
                    -1,
                    false,
                    null
            );

            List<QuestionModel> randomQuestions = questionRepositoryPort.getRandomQuestions(3);

            Long savedAttemptId = attemptRepositoryPort.save(attemptModel);
            return new StartAttemptModel(
                    null,
                    null,
                    savedAttemptId,
                    randomQuestions
            );
        } else {
            throw new IllegalArgumentException("Invalid exam or user ID");
        }
    }
}
