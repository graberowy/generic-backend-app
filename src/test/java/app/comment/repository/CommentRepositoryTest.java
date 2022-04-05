package app.comment.repository;

import app.organisation.repository.Organisation;
import app.organisation.repository.OrganisationRepository;
import app.post.repository.Post;
import app.post.repository.PostRepository;
import app.user.repository.User;
import app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void when_add_comment_with_existing_user_and_post_comment_should_be_added() {
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

        //when
        List<Comment> commentList = commentRepository.findAll();
        //then
        assertEquals(2, commentList.size());
    }

    @Test
    void when_delete_user_all_comments_related_to_should_be_removed() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("some organisation");

        organisationRepository.save(organisation);

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("jan@wp.pl");
        user.setOrganisation(organisation);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBody("sample text");

        Comment secondComment = new Comment();
        secondComment.setUser(user);
        secondComment.setBody("sample text2");

        user.setComments(List.of(comment, secondComment));

        userRepository.save(user);

        commentRepository.save(comment);
        commentRepository.save(secondComment);

        userRepository.delete(user);
        //when
        List<User> userList = userRepository.findAll();
        List<Comment> commentList = commentRepository.findAll();
        //then
        assertTrue(userList.isEmpty());
        assertTrue(commentList.isEmpty());
    }
}