package app.post.web.resources;

import app.comment.repository.Comment;
import app.comment.web.resources.CommentDTO;
import app.generic.web.resources.GenericMapper;
import app.post.repository.Post;
import app.user.repository.User;
import app.user.web.resources.UserDTO;
import org.mapstruct.*;

/**
 * This interface is for map post to postDTO and reverse
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PostMapper extends GenericMapper<PostDTO, Post> {

    /**
     * This method mapping post to postDTO with basic mapping type
     *
     * @param post object
     * @return postDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "createAt", target = "createAt")
    @BeanMapping(ignoreByDefault = true)
    PostDTO mapToPostDTOBasic(Post post);

    /**
     * This method mapping post to postDTO with details mapping type
     *
     * @param post object
     * @return postDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "createAt", target = "createAt")
    @Mapping(source = "comments", target = "comments")
    @BeanMapping(ignoreByDefault = true)
    PostDTO mapToPostDTODetails(Post post);

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
     * This method mapping comment to commentDTO
     *
     * @param comment object
     * @return commentDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "createAt", target = "createAt")
    @BeanMapping(ignoreByDefault = true)
    CommentDTO mapToCommentDTO(Comment comment);


    /**
     * This method work conversely to mapToPostDTOBasic
     *
     * @param postDTO object
     * @return post
     */
    @InheritInverseConfiguration(name = "mapToPostDTOBasic")
    Post mapToPostBasic(PostDTO postDTO);

    /**
     * This method work conversely to mapToPostDTODetails
     *
     * @param postDTO object
     * @return post
     */
    @InheritInverseConfiguration(name = "mapToPostDTODetails")
    Post mapToPostDetails(PostDTO postDTO);

    /**
     * This method work conversely to mapToUserDTO
     *
     * @param userDTO object
     * @return user
     */
    @InheritInverseConfiguration(name = "mapToUserDTO")
    User mapToUser(UserDTO userDTO);

    /**
     * This method work conversely to mapToCommentDTO
     *
     * @param commentDTO object
     * @return comment
     */
    @InheritInverseConfiguration(name = "mapToCommentDTO")
    Comment mapToComment(CommentDTO commentDTO);
}
