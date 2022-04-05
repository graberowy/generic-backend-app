package app.address.model;

import app.address.client.Address;
import app.user.repository.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressTotal extends User {
    private Address address;

    public UserAddressTotal(User user, Address address) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setOrganisation(user.getOrganisation());
        this.setRoles(user.getRoles());
        this.address = address;
    }

    public UserAddressTotal(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setOrganisation(user.getOrganisation());
        this.setRoles(user.getRoles());
    }

    public UserAddressTotal() {
    }
}
