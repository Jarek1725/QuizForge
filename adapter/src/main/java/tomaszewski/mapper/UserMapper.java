package tomaszewski.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tomaszewski.model.UserModel;
import tomaszewski.out.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserModel toUserModel(UserEntity userEntity);


    UserEntity toUserEntity(UserModel userModel);
}
