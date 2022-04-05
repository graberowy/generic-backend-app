package app.comment.service;

import app.comment.repository.Comment;
import app.generic.service.GenericService;

import java.util.List;

/**
 * This interface extends service methods on comment
 */
public interface CommentService extends GenericService<Comment, Long> {
    /**
     * This method is for get all comments related to same post
     *
     * @param id from post object
     * @return list of comments
     */
    List<Comment> findByPostId(Long id);

}
