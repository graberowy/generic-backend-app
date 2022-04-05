package app.post.repository;

import app.comment.repository.Comment;
import app.comment.repository.CommentRepository;
import app.organisation.repository.Organisation;
import app.organisation.repository.OrganisationRepository;
import app.user.repository.User;
import app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void when_add_post_by_existing_user_than_new_post_should_be_added() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("some organisation");

        organisationRepository.save(organisation);

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("jan@wp.pl");
        user.setOrganisation(organisation);

        userRepository.save(user);

        Post post = new Post();
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        postRepository.save(post);
        //when
        List<Post> postList = postRepository.findAll();
        //then
        assertEquals(1, postList.size());
        assertNotNull(postList.get(0).getId());
        assertTrue(postList.get(0).getCreateAt().isBefore(Instant.now()));
        assertEquals("title test", postList.get(0).getTitle());
        assertEquals("ala ma kota", postList.get(0).getBody());
        assertEquals(user, postList.get(0).getUser());

    }

    @Test
    void when_add_post_with_no_existing_user_than_user_should_not_be_added() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("jan@wp.pl");
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        postRepository.save(post);
        //when
        //then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            userRepository.findAll();
        });
    }

    @Test
    void when_add_post_with_no_existing_comment_than_comment_should_not_be_added() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("jan@wp.pl");
        user.setOrganisation(organisation);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBody("sample text");

        Post post = new Post();
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);
        post.setComments(List.of(comment));

        postRepository.save(post);
        //when
        //then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            commentRepository.findAll();
        });
    }

    @Test
    void when_delete_user_all_post_created_by_user_should_be_removed() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("some organisation");

        organisationRepository.save(organisation);

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("jan@wp.pl");
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        user.setPosts(Set.of(post));

        userRepository.save(user);
        postRepository.save(post);

        userRepository.delete(user);
        //when
        List<Post> postList = postRepository.findAll();
        //then
        assertTrue(postList.isEmpty());
    }

    @Test
    void when_delete_post_all_comments_related_to_should_be_removed() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("some organisation");

        organisationRepository.save(organisation);

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("jan@wp.pl");
        user.setOrganisation(organisation);

        userRepository.save(user);

        Post post = new Post();
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBody("sample text");

        Comment secondComment = new Comment();
        secondComment.setUser(user);
        secondComment.setBody("sample text2");

        post.setComments(List.of(comment, secondComment));

        postRepository.save(post);
        commentRepository.save(comment);
        commentRepository.save(secondComment);

        postRepository.delete(post);
        //when
        List<Post> postList = postRepository.findAll();
        List<Comment> commentList = commentRepository.findAll();
        //then
        assertTrue(postList.isEmpty());
        assertTrue(commentList.isEmpty());
    }


}