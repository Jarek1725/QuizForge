package tomaszewski.usecase;

import tomaszewski.model.UserModel;

public interface UserUseCase {
    UserModel findUserById(Long id);

    void registerUser(UserModel userModel);
}
