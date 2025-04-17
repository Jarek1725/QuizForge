package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tomaszewski.out.entities.QuestionEntity;

public interface JpaQuestionRepository extends JpaRepository<QuestionEntity, Long> {
}
