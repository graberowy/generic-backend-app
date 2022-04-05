package app.role.web.resources;

import app.generic.web.resources.DataDTO;
import app.role.repository.Permission;
import app.user.web.resources.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO extends DataDTO<Long> {

    private String name;
    private Set<Permission> permissions;
    private Set<UserDTO> users;
}
