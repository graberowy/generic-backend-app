package app.comment.service;

import app.comment.repository.Comment;
import app.comment.repository.CommentRepository;
import app.generic.service.GenericCRUD;
import app.post.service.PostServiceBasic;
import app.user.service.UserServiceBasic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceBasic extends GenericCRUD<Comment, Long> implements CommentService {
    private final CommentRepository commentRepository;
    private final PostServiceBasic postServiceBasic;
    private final UserServiceBasic userServiceBasic;

    /**
     * This is constructor for use generic operations on specific type
     *  @param commentRepository specified type interface
     * @param postServiceBasic post service
     * @param userServiceBasic user service
     */
    public CommentServiceBasic(CommentRepository commentRepository, PostServiceBasic postServiceBasic, UserServiceBasic userServiceBasic) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.postServiceBasic = postServiceBasic;
        this.userServiceBasic = userServiceBasic;
    }

    /**
     * This method is to add new comment
     * @param comment object
     * @return new comment record
     */
    @Override
    public Comment save(Comment comment) {
        comment.setId(null);
        if (getOrganisationIdFromPost(comment).equals(getOrganisationIdFromUser(comment))) {
            return commentRepository.save(comment);
        } else { throw new IllegalArgumentException("organisation mismatch"); }
    }

    /**
     * This method is for get organisation id from post object
     * @param comment object
     * @return organisation id
     */
    private Long getOrganisationIdFromPost (Comment comment) {
      return postServiceBasic.findById(comment.getPost().getId())
              .map(post -> post.getUser().getOrganisation().getId())
              .orElseThrow(() -> new IllegalArgumentException("post id out of bound"));

    }

    /**
     * This method is for get organisation id from user object
     * @param comment object
     * @return organisation id
     */
    private Long getOrganisationIdFromUser (Comment comment) {
        return userServiceBasic.findById(comment.getUser().getId())
                .map(user -> user.getOrganisation().getId())
                .orElseThrow(() -> new IllegalArgumentException("user id out of bound"));
    }

    /**
     * This method is for update specify instances of comment
     * @param comment object
     * @return updated comment record
     */
    @Override
    public Optional<Comment> update(Comment comment) {
        return commentRepository.findById(comment.getId())
                .map(commentToUpdate -> saveChangesToComment(comment, commentToUpdate));
    }

    /**
     * This method is for save changes to comment record
     * @param comment object
     * @param commentToUpdate object
     * @return comment after update
     */
    private Comment saveChangesToComment(Comment comment, Comment commentToUpdate) {
        if (comment.getBody() != null) { commentToUpdate.setBody(comment.getBody()); }
        return commentRepository.save(commentToUpdate);
    }

    /**
     * This method is for find all comment objects related to same user
     * @param id from post object
     * @return list of comments
     */
    @Override
    public List<Comment> findByPostId(Long id) {
        return commentRepository.findByPostId(id);
    }
}
