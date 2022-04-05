package app.user.web.resources;

import app.generic.web.resources.DataDTO;
import app.organisation.web.resources.OrganisationDTO;
import app.role.web.resources.RoleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends DataDTO<Long> {

    private String username;
    private String email;
    private OrganisationDTO organisation;
    private Set<RoleDTO> roles;
}
