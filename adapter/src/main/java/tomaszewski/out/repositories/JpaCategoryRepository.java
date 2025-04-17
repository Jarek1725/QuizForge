package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tomaszewski.out.entities.CategoryEntity;

import java.util.Optional;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
}
