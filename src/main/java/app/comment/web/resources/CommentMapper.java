package app.comment.web.resources;

import app.comment.repository.Comment;
import app.generic.web.resources.GenericMapper;
import app.post.repository.Post;
import app.post.web.resources.PostDTO;
import app.user.repository.User;
import app.user.web.resources.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This interface is for map comment to commentDTO and reverse
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends GenericMapper<CommentDTO, Comment> {
    /**
     * This method mapping comment to commentDTO
     *
     * @param comment object
     * @return commentDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "post", target = "post")
    @Mapping(source = "createAt", target = "createAt")
    @BeanMapping(ignoreByDefault = true)
    CommentDTO mapToCommentDTO(Comment comment);

    /**
     * This method mapping user to userDTO
     *
     * @param user object
     * @return userDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @BeanMapping(ignoreByDefault = true)
    UserDTO mapToUserDTO(User user);

    /**
     * This method mapping post to postDTO
     *
     * @param post object
     * @return postDTO
     */
    @Mapping(source = "id", target = "id")
    @BeanMapping(ignoreByDefault = true)
    PostDTO mapToPostDTO(Post post);

    /**
     * This method work conversely to mapToCommentDTO
     *
     * @param commentDTO object
     * @return comment
     */
    @InheritInverseConfiguration(name = "mapToCommentDTO")
    Comment mapToComment(CommentDTO commentDTO);

    /**
     * This method work conversely to mapToUserDTO
     *
     * @param userDTO object
     * @return user
     */
    @InheritInverseConfiguration(name = "mapToUserDTO")
    User mapToUser(UserDTO userDTO);

    /**
     * This method work conversely to mapToPostDTO
     *
     * @param postDTO object
     * @return post
     */
    @InheritInverseConfiguration(name = "mapToPostDTO")
    Post mapToPost(PostDTO postDTO);
}
