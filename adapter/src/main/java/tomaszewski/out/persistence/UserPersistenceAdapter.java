package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tomaszewski.model.UserModel;
import tomaszewski.port.out.UserRepositoryPort;
import tomaszewski.mapper.UserMapper;
import tomaszewski.out.repositories.JpaUserRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserModel> findUserById(Long id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::toUserModel);
    }
}
