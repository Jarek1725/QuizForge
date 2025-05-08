package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tomaszewski.out.entities.UserAnswerEntity;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswerEntity, Long> {
}
