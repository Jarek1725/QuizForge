package tomaszewski.in;

import tomaszewski.model.UserModel;

public interface UserRepositoryUseCase {
    UserModel findUserById(Long id);
}
