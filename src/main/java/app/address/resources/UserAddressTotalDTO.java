package app.address.resources;

import app.user.web.resources.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAddressTotalDTO extends UserDTO {
    private AddressDTO address;

    public UserAddressTotalDTO(UserDTO userDTO, AddressDTO addressDTO) {
        this.setId(userDTO.getId());
        this.setUsername(userDTO.getUsername());
        this.setEmail(userDTO.getEmail());
        this.setOrganisation(userDTO.getOrganisation());
        this.setRoles(userDTO.getRoles());
        this.address = addressDTO;
    }

    public UserAddressTotalDTO() {
    }
}
