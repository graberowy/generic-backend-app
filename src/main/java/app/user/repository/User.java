package app.user.repository;

import app.comment.repository.Comment;
import app.data.Data;
import app.organisation.repository.Organisation;
import app.post.repository.Post;
import app.role.repository.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends Data<Long> {

    @Column(unique = true)
    @NotBlank(message = "name required")
    private String username;
    @Column(unique = true)
    private String email;
    @ManyToOne
    private Organisation organisation;
    @NotNull
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @NotNull
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();
    @NotNull
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @PreRemove
    private void removeRoleFromUser() {
        roles.forEach(role -> role.getUsers().remove(this));

    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        role.getUsers().remove(this);
        roles.remove(role);
    }

    public void setPosts(Set<Post> posts) {
        this.posts.clear();
        this.posts.addAll(posts);
    }

    public void setComments(List<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
    }


}
