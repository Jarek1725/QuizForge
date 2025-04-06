package tomaszewski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.in.UserRepositoryUseCase;
import tomaszewski.model.UserModel;
import tomaszewski.out.UserRepositoryPort;

@Service
@RequiredArgsConstructor
public class UserService implements UserRepositoryUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserModel findUserById(Long id) {
        return userRepositoryPort.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
