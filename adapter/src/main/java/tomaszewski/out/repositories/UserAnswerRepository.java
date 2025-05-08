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
}
