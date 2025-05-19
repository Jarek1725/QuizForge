package tomaszewski.port.out;

import tomaszewski.model.RoleModel;

import java.util.List;

public interface RoleRepositoryPort {
    List<RoleModel> findAllByNames(List<String> roleNames);
}
