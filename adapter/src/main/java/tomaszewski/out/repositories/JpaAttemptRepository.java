package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tomaszewski.out.entities.AttemptEntity;

import java.util.List;

@Repository

public interface JpaAttemptRepository extends JpaRepository<AttemptEntity, Long> {
    List<AttemptEntity> findAttemptEntitiesByUserIdOrderByStartTimeDesc(Long userId);
}
