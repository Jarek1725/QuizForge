package tomaszewski.mapper;

import org.mapstruct.Mapper;
import tomaszewski.model.RoleModel;
import tomaszewski.out.entities.RoleEntity;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    default RoleModel map(String name) {
        return new RoleModel(null, name);
    }

    RoleModel toRoleModel(RoleEntity roleEntity);
}
