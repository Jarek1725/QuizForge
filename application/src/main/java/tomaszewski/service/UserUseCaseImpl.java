package tomaszewski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.usecase.UserUseCase;
import tomaszewski.model.UserModel;
import tomaszewski.port.out.UserRepositoryPort;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserModel findUserById(Long id) {
        return userRepositoryPort.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
