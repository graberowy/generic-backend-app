package app.comment.web.resources;

import app.generic.web.resources.DataDTO;
import app.post.web.resources.PostDTO;
import app.user.web.resources.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO extends DataDTO<Long> {
    private String body;
    private UserDTO user;
    private Instant createAt;
    private PostDTO post;
}
