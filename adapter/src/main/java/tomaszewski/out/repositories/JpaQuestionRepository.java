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

    @Query(value = "SELECT * FROM question ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<QuestionEntity> findRandomQuestionsLimit(@Param("limit") int limit);
}
