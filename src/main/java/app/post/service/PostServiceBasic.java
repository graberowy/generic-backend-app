package app.post.service;

import app.generic.service.GenericCRUD;
import app.post.repository.Post;
import app.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceBasic extends GenericCRUD<Post, Long> implements PostService {

    private final PostRepository postRepository;

    /**
     * This is constructor for use generic operations on specific type
     *
     * @param postRepository specified type interface
     */
    public PostServiceBasic(PostRepository postRepository) {
        super(postRepository);
        this.postRepository = postRepository;
    }

    /**
     * This method is for update only specify instances of post
     * @param post object
     * @return updated post object
     */
    @Override
    public Optional<Post> update(Post post) {
        return postRepository.findById(post.getId())
                .map(postToUpdate -> saveChangesToPost(post, postToUpdate));
    }

    /**
     * This method is for save changes to updating object
     * @param post object
     * @param postToUpdate object
     * @return updated post object
     */
    private Post saveChangesToPost(Post post, Post postToUpdate) {
        if (post.getTitle() != null) { postToUpdate.setTitle(post.getTitle()); }
        if (post.getBody() != null) { postToUpdate.setBody(post.getBody()); }
        return postRepository.save(postToUpdate);
    }

    /**
     * This method is for get all posts related to same user
     * @param id from user object
     * @return list of posts
     */
    @Override
    public List<Post> getByUserId(Long id) {
        return postRepository.getByUserId(id);
    }
}
