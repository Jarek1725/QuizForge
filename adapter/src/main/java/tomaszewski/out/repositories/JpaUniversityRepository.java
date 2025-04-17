package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tomaszewski.out.entities.UniversityEntity;

import java.util.Optional;

public interface JpaUniversityRepository extends JpaRepository<UniversityEntity, Integer> {
    Optional<UniversityEntity> findByName(String name);
}
