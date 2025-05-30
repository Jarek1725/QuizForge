package tomaszewski.port.out;

import tomaszewski.model.AttemptModel;

import java.util.List;
import java.util.Optional;

public interface AttemptRepositoryPort {
    List<AttemptModel> findLastAttemptsByUser(Long userId);
    AttemptModel findLastAttemptByUser(Long userId);

    List<AttemptModel> findLastAttemptsByUserAndScoreMoreThan0(Long userId);

    AttemptModel save(AttemptModel attemptModel, Boolean isExam);

    Optional<AttemptModel> findAttemptById(Long attemptId);
    Long findAttemptSumForExam(Long examId);
    Double findAverageExamScore(Long examId);

    Long findPassedExamCount(Long examId);
}
