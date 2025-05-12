package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tomaszewski.out.entities.AnswerOptionEntity;

import java.util.List;

public interface JpaAnswerOptionRepository extends JpaRepository<AnswerOptionEntity, Long> {
    @Query("SELECT a FROM answer_option a WHERE a.question.id = :id")
    List<AnswerOptionEntity> findAllByQuestionId(@Param("id") Long id);

    List<AnswerOptionEntity> findAllByIdIn(List<Long> userAnswersIds);
}
