package app.organisation.web.resources;

import app.generic.web.resources.DataDTO;
import app.user.web.resources.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganisationDTO extends DataDTO<Long> {

    private String name;
    private Set<UserDTO> users;

}
