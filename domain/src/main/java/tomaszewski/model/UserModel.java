package tomaszewski.model;

import java.util.List;


//Long id,
//String email,
//List<RoleModel> roles



public class UserModel{
    private Long id;
    private String email;
    private List<RoleModel> roles;
    private String password;

    public UserModel(Long id, String email, List<RoleModel> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = null;
    }

    public UserModel(Long id, String email, List<RoleModel> roles, String password) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public UserModel() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }
}