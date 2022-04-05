package app.post.service;

import app.generic.service.GenericService;
import app.post.repository.Post;

import java.util.List;

/**
 * This interface extends service methods on post
 */
public interface PostService extends GenericService<Post, Long> {
    /**
     * This method is for get all posts related to same user
     *
     * @param id from user object
     * @return list of posts
     */
    List<Post> getByUserId(Long id);
}
