package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tomaszewski.out.entities.UserEntity;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends CrudRepository<UserEntity, Long> {


    @Query("SELECT u FROM users u LEFT JOIN FETCH u.roles WHERE u.email = ?1")
    Optional<UserEntity> findByEmailAndRoles(String email);


}
