package tomaszewski.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import tomaszewski.model.UserModel;
import tomaszewski.openapi.model.RegisterRequest;
import tomaszewski.openapi.model.UserData;
import tomaszewski.security.UserSecurityDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SecurityUserMapper {

    @Mapping(target = "email", source = "userSecurityDetails.username")
    @Mapping(target = "roles", source = "userSecurityDetails.authorities")
    UserData toUserData(UserSecurityDetails userSecurityDetails);

    default List<String> authoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    UserModel toUserModel(RegisterRequest registerRequest);
}
