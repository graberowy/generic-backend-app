package app.role.repository;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import app.data.Data;
import app.user.repository.User;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends Data<Long> {

    @NotBlank(message = "name required")
    private String name;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Permission.class)
    private Set<Permission> permissions = new HashSet<>();
    @NotNull
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @PreRemove
    private void removeRoleFromUser() {
        users.forEach(user -> user.getRoles().remove(this));
    }

    public void addUser(User user) {
        user.getRoles().add(this);
        users.add(user);
    }

    public void removeUser(User user) {
        user.getRoles().remove(this);
        users.remove(user);
    }


}
