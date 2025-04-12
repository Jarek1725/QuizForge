package tomaszewski.in;

import tomaszewski.model.UserModel;

public interface UserUseCase {
    UserModel findUserById(Long id);
}
