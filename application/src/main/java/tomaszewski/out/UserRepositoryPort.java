package tomaszewski.out;

import tomaszewski.model.UserModel;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<UserModel> findUserById(Long id);
}
