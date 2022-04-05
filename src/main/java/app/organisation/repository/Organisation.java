package app.organisation.repository;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import app.data.Data;
import app.user.repository.User;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "organisations")
public class Organisation extends Data<Long> {

    @Column(unique = true)
    @NotBlank(message = "name required")
    private String name;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    private Set<User> users = new HashSet<>();


    public void setUsers(Set<User> users) {
        this.users.clear();
        this.users.addAll(users);
    }
}
