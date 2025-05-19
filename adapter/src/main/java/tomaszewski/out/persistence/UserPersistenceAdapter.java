package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import tomaszewski.model.RoleModel;
import tomaszewski.model.UserModel;
import tomaszewski.out.entities.UserEntity;
import tomaszewski.port.out.RoleRepositoryPort;
import tomaszewski.port.out.UserRepositoryPort;
import tomaszewski.mapper.UserMapper;
import tomaszewski.out.repositories.JpaUserRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;
    private final RoleRepositoryPort roleRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserModel> findUserById(Long id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::toUserModel);
    }

    @Override
    public void save(UserModel userModel) {
        List<RoleModel> userRole = roleRepositoryPort.findAllByNames(List.of("USER"));
        userModel.setRoles(userRole);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        UserEntity userEntity = userMapper.toUserEntity(userModel);
        jpaUserRepository.save(userEntity);
    }
}
