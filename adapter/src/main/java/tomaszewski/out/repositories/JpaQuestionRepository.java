package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tomaszewski.out.entities.QuestionEntity;

import java.util.List;
import java.util.Optional;

public interface JpaQuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query("SELECT a.question FROM answer_option a WHERE a.id IN ( :answerId )")
    Optional<QuestionEntity> findQuestionByAnswers(@Param("answerId") List<Long> answerId);

    @Query(value = """
            SELECT q.*
            FROM question q
            JOIN exam e ON e.id = q.exam_id
            WHERE e.id = :examId
            ORDER BY RANDOM()
            LIMIT :limit
            """, nativeQuery = true)
    List<QuestionEntity> findRandomQuestionsLimit(@Param("limit") long limit, @Param("examId") Long examId);

    @Query("SELECT q FROM question q JOIN UserAnswerEntity ua ON ua.question.id = q.id WHERE ua.attempt.id = :attemptId")
    List<QuestionEntity> findAllQuestionsByAttemptId(@Param("attemptId") Long attemptId);

    @Query(value = """
            SELECT q.*
            FROM question q
            JOIN exam e ON e.id = q.exam_id
            WHERE e.id = :examId
              AND q.id NOT IN (
                  SELECT ua.question_id
                  FROM user_answers ua
                  JOIN attempt a ON ua.attempt_id = a.id
                  WHERE a.user_id = :userId
                  AND a.is_exam = true
              )
            ORDER BY RANDOM()
            LIMIT :limit
            """, nativeQuery = true)
    List<QuestionEntity> findNewRandomQuestionsLimit(@Param("limit") long limit,
                                                     @Param("examId") Long examId,
                                                     @Param("userId") Long userId);

}
