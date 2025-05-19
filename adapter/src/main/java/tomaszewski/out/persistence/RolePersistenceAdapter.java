package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tomaszewski.mapper.RoleMapper;
import tomaszewski.model.RoleModel;
import tomaszewski.out.entities.RoleEntity;
import tomaszewski.out.repositories.JpaRoleRepository;
import tomaszewski.port.out.RoleRepositoryPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RoleRepositoryPort {

    private final JpaRoleRepository jpaRoleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleModel> findAllByNames(List<String> roleNames) {
        List<RoleEntity> allByNameIn = jpaRoleRepository.findAllByNameIn(roleNames);
        return allByNameIn.stream()
                .map(roleMapper::toRoleModel)
                .toList();
    }
}
