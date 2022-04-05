package app.comment.repository;

import app.data.Data;
import app.post.repository.Post;
import app.user.repository.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends Data<Long> {
    private String body;
    @ManyToOne
    private User user;
    @Column(name = "create_at", nullable = false)
    private Instant createAt;
    @ManyToOne
    private Post post;

    @PrePersist
    private void onCreate() {
        createAt = Instant.now();
    }
}
