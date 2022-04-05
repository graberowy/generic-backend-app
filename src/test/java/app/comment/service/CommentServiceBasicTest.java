package app.comment.service;

import app.comment.repository.Comment;
import app.comment.repository.CommentRepository;
import app.organisation.repository.Organisation;
import app.post.repository.Post;
import app.post.service.PostServiceBasic;
import app.user.repository.User;
import app.user.service.UserServiceBasic;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class CommentServiceBasicTest {

    private final CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
    private final PostServiceBasic postServiceBasic = Mockito.mock(PostServiceBasic.class);
    private final UserServiceBasic userServiceBasic = Mockito.mock(UserServiceBasic.class);
    private final CommentServiceBasic commentServiceBasic = new CommentServiceBasic(commentRepository, postServiceBasic, userServiceBasic);

    @Test
    void when_send_request_with_valid_id_then_comment_with_provided_id_should_be_returned() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPost(post);
        comment.setUser(user);
        comment.setBody("test text");

        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        //when
        Optional<Comment> commentReturned = commentServiceBasic.findById(1L);
        //then
        assertEquals("test text", commentReturned.get().getBody());
        assertEquals(1L, commentReturned.get().getId());
        assertEquals(user, commentReturned.get().getUser());
    }

    @Test
    void when_add_new_comment_than_comment_should_be_added_to_repo() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        organisation.setName("some organisation");

        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setBody("test text");

        Comment secondComment = new Comment();
        secondComment.setId(1L);
        secondComment.setPost(post);
        secondComment.setUser(user);
        secondComment.setBody("test text");

        Mockito.when(postServiceBasic.findById(1L)).thenReturn(Optional.of(post));
        Mockito.when(userServiceBasic.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(commentRepository.save(comment)).thenReturn(secondComment);
        //when
        Comment commentSaved = commentServiceBasic.save(comment);
        //then
        assertEquals("test text", commentSaved.getBody());
        assertEquals(1L, commentSaved.getId());
        assertEquals(user, commentSaved.getUser());
        Mockito.verify(commentRepository).save(comment);
    }

    @Test
    void when_add_new_comment_with_different_organisation_id_than_comment_should_not_be_added_to_repo() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        organisation.setName("some organisation");

        Organisation secondOrganisation = new Organisation();
        secondOrganisation.setId(1L);
        secondOrganisation.setName("organisation");

        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");
        user.setOrganisation(organisation);

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("Sebastian Kowalski");
        secondUser.setEmail("seba@wp.pl");
        secondUser.setOrganisation(secondOrganisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(secondUser);
        comment.setBody("test text");

        Comment secondComment = new Comment();
        secondComment.setId(1L);
        secondComment.setPost(post);
        secondComment.setUser(secondUser);
        secondComment.setBody("test text");

        Mockito.when(postServiceBasic.findById(1L)).thenReturn(Optional.of(post));
        Mockito.when(userServiceBasic.findById(2L)).thenReturn(Optional.of(secondUser));
        Mockito.when(commentRepository.save(comment)).thenReturn(secondComment);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            commentServiceBasic.save(comment);
        }, "organisation mismatch");
    }

    @Test
    void when_add_new_comment_with_no_existing_user_id_than_comment_should_not_be_added_to_repo(){
        //given
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        organisation.setName("some organisation");

        User user = new User();
        user.setId(10L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setBody("test text");

        Comment secondComment = new Comment();
        secondComment.setId(1L);
        secondComment.setPost(post);
        secondComment.setUser(user);
        secondComment.setBody("test text");

        Mockito.when(postServiceBasic.findById(1L)).thenReturn(Optional.of(post));
        Mockito.when(userServiceBasic.findById(10L)).thenReturn(Optional.empty());
        Mockito.when(commentRepository.save(comment)).thenReturn(secondComment);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            commentServiceBasic.save(comment);
        }, "user id out of bound");
    }

    @Test
    void when_add_new_comment_with_no_existing_post_id_than_comment_should_not_be_added_to_repo(){
        //given
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        organisation.setName("some organisation");

        User user = new User();
        user.setId(10L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setBody("test text");

        Comment secondComment = new Comment();
        secondComment.setId(1L);
        secondComment.setPost(post);
        secondComment.setUser(user);
        secondComment.setBody("test text");

        Mockito.when(postServiceBasic.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(userServiceBasic.findById(10L)).thenReturn(Optional.of(user));
        Mockito.when(commentRepository.save(comment)).thenReturn(secondComment);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            commentServiceBasic.save(comment);
        }, "post id out of bound");
    }

    @Test
    void when_update_existing_comment_then_data_should_be_updated() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Comment firstComment = new Comment();
        firstComment.setId(1L);
        firstComment.setPost(post);
        firstComment.setUser(user);
        firstComment.setBody("test text");

        Comment secondComment = new Comment();
        secondComment.setId(1L);
        secondComment.setPost(post);
        secondComment.setUser(user);
        secondComment.setBody("new body updated");

        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(firstComment));
        Mockito.when(commentRepository.save(any())).thenReturn(secondComment);
        //when
        Optional<Comment> commentUpdated = commentServiceBasic.update(secondComment);
        //then
        assertEquals("new body updated", commentUpdated.get().getBody());
        assertEquals(1L, commentUpdated.get().getId());
        assertEquals(user, commentUpdated.get().getUser());
    }

    @Test
    void when_request_find_all_than_all_comments_should_be_returned() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Comment firstComment = new Comment();
        firstComment.setId(1L);
        firstComment.setPost(post);
        firstComment.setUser(user);
        firstComment.setBody("test text");

        Comment secondComment = new Comment();
        secondComment.setId(2L);
        secondComment.setPost(post);
        secondComment.setUser(user);
        secondComment.setBody("new body updated");

        Mockito.when(commentRepository.findAll()).thenReturn(List.of(firstComment, secondComment));
        //when
        List<Comment> commentList = commentServiceBasic.findAll();
        //then
        assertFalse(commentList.isEmpty());
        assertEquals(2, commentList.size());
    }

    @Test
    void when_delete_comment_by_id_then_comment_should_be_removed() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Comment firstComment = new Comment();
        firstComment.setId(1L);
        firstComment.setPost(post);
        firstComment.setUser(user);
        firstComment.setBody("test text");

        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(firstComment));
        //when
        commentServiceBasic.delete(1L);
        //then
        Mockito.verify(commentRepository).deleteById(1L);
    }

    @Test
    void when_use_findByPostId_than_all_comments_with_valid_post_id_should_be_returned() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Post secondPost = new Post();
        post.setId(2L);
        post.setTitle("title test second");
        post.setBody("ala ma kota!!!!");
        post.setUser(user);

        Comment firstComment = new Comment();
        firstComment.setId(1L);
        firstComment.setPost(post);
        firstComment.setUser(user);
        firstComment.setBody("test text");

        Comment secondComment = new Comment();
        secondComment.setId(2L);
        secondComment.setPost(post);
        secondComment.setUser(user);
        secondComment.setBody("new body updated");

        Comment thirdComment = new Comment();
        thirdComment.setId(3L);
        thirdComment.setPost(secondPost);
        thirdComment.setUser(user);
        thirdComment.setBody("new body third test");

        Mockito.when(commentRepository.findByPostId(1L)).thenReturn(List.of(firstComment, secondComment));
        //when
        List<Comment> commentList = commentServiceBasic.findByPostId(1L);
        //then
        assertEquals(2, commentList.size());
    }

}