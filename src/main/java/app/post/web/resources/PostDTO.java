package app.post.web.resources;

import app.comment.web.resources.CommentDTO;
import app.generic.web.resources.DataDTO;
import app.user.web.resources.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO extends DataDTO<Long> {
    private String title;
    private String body;
    private UserDTO user;
    private Instant createAt;
    private List<CommentDTO> comments;
}
