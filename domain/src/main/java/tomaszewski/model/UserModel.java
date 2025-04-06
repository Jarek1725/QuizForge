package tomaszewski.model;

import java.util.List;

public record UserModel(
        Long id,
        String email,
        List<Role> roles
) {
}
