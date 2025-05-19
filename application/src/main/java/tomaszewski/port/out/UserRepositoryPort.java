package tomaszewski.port.out;

import tomaszewski.model.UserModel;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<UserModel> findUserById(Long id);

    void save(UserModel userModel);
}
