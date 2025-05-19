package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tomaszewski.out.entities.RoleEntity;

import java.util.List;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findAllByNameIn(List<String> roleNames);
}
