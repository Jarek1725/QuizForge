package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tomaszewski.out.entities.AttemptEntity;

import java.util.List;

@Repository

public interface JpaAttemptRepository extends JpaRepository<AttemptEntity, Long> {
    List<AttemptEntity> findByUserIdOrderByStartTimeDesc(Long userId);
    AttemptEntity findFirstByUserIdOrderByStartTimeDesc(Long userId);
    List<AttemptEntity> findByUserIdAndScoreGreaterThan(Long userId, Integer score);

    @Query("SELECT COUNT(a) FROM attempt a WHERE a.exam.id = :examId")
    Long countAttemptsPerExamId(@Param("examId") Long examId);

    @Query("SELECT AVG(a.score * 1.0 / a.maxScore) FROM attempt a WHERE a.exam.id = :examId AND a.maxScore > 0")
    Double findAverageScoreByExamId(@Param("examId") Long examId);

    @Query("SELECT COUNT(a) FROM attempt a " +
            "JOIN exam e " +
            "WHERE a.exam.id = :examId AND a.maxScore > 0 AND (a.score * 1.0 / a.maxScore) * 100 >= e.percentageToPass")
    Long countPassedAttemptsByExamId(@Param("examId") Long examId);
}
