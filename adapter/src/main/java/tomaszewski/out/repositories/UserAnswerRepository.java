package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tomaszewski.out.entities.UserAnswerEntity;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswerEntity, Long> {
    @Query("SELECT ua FROM UserAnswerEntity ua WHERE ua.attempt.id = :attemptId")
    List<UserAnswerEntity> findAllByAttemptId(@Param("attemptId") Long attemptId);

    @Query(value = """
    SELECT ua.*
    FROM user_answers ua
    JOIN attempt a ON ua.attempt_id = a.id
    WHERE a.user_id = :userId
      AND a.exam_id = :examId
      AND ua.id IN (
        SELECT ua2.id
        FROM user_answers ua2
        JOIN attempt a2 ON ua2.attempt_id = a2.id
        LEFT JOIN user_answer_selected_options uaso2 ON ua2.id = uaso2.user_answer_id
        LEFT JOIN answer_option ao_user ON uaso2.answer_option_id = ao_user.id
        WHERE a2.user_id = :userId
        GROUP BY ua2.id, ua2.question_id
        HAVING ARRAY_AGG(ao_user.id ORDER BY ao_user.id) IS DISTINCT FROM (
          SELECT ARRAY_AGG(ao.id ORDER BY ao.id)
          FROM answer_option ao
          WHERE ao.question_id = ua2.question_id AND ao.correct = true
        )
      )
    """, nativeQuery = true)
    List<UserAnswerEntity> findIncorrectAnswersByUserAndExam(
            @Param("userId") Long userId,
            @Param("examId") Long examId
    );


    @Query(value = """
    SELECT ua.*
    FROM user_answers ua
             JOIN attempt a ON ua.attempt_id = a.id
    WHERE a.user_id = :userId
      AND a.exam_id = :examId
      AND ua.id IN (
        SELECT DISTINCT ON (ua2.question_id) ua2.id
        FROM user_answers ua2
                 JOIN attempt a2 ON ua2.attempt_id = a2.id
                 LEFT JOIN user_answer_selected_options uaso2 ON ua2.id = uaso2.user_answer_id
                 LEFT JOIN answer_option ao_user ON uaso2.answer_option_id = ao_user.id
        WHERE a2.user_id = :userId
        ORDER BY ua2.question_id, ua2.attempt_id DESC
    )
      AND ua.id IN (
        SELECT ua3.id
        FROM user_answers ua3
                 JOIN attempt a3 ON ua3.attempt_id = a3.id
                 LEFT JOIN user_answer_selected_options uaso3 ON ua3.id = uaso3.user_answer_id
                 LEFT JOIN answer_option ao_user3 ON uaso3.answer_option_id = ao_user3.id
        WHERE a3.user_id = :userId
        GROUP BY ua3.id, ua3.question_id
        HAVING ARRAY_AGG(ao_user3.id ORDER BY ao_user3.id) = (
            SELECT ARRAY_AGG(ao.id ORDER BY ao.id)
            FROM answer_option ao
            WHERE ao.question_id = ua3.question_id AND ao.correct = true
        )
    )
    """, nativeQuery = true)
    List<UserAnswerEntity> findLatestCorrectAnswers(@Param("userId") Long userId, @Param("examId") Long examId);


}
