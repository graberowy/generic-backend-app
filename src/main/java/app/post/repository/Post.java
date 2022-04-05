package app.post.repository;

import app.comment.repository.Comment;
import app.data.Data;
import app.user.repository.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class Post extends Data<Long> {
    @Column(unique = true)
    @NotBlank(message = "title required")
    private String title;
    private String body;
    @ManyToOne
    private User user;
    @Column(name = "create_at", nullable = false)
    private Instant createAt;
    @NotNull
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    private void onCreate() {
        createAt = Instant.now();
    }

    public void setComments(List<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
    }


}
