package app.post.service;

import app.post.repository.Post;
import app.post.repository.PostRepository;
import app.user.repository.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

class PostServiceBasicTest {

    private final PostRepository postRepository = Mockito.mock(PostRepository.class);
    private final PostServiceBasic postServiceBasic = new PostServiceBasic(postRepository);

    @Test
    void when_send_request_with_valid_id_then_post_with_provided_id_should_be_returned() {
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

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        //when
        Optional<Post> postReturned = postServiceBasic.findById(1L);
        //then
        assertEquals("title test", postReturned.get().getTitle());
        assertEquals("ala ma kota", postReturned.get().getBody());
        assertEquals(1L, postReturned.get().getId());
        assertEquals(user, postReturned.get().getUser());
    }

    @Test
    void when_add_new_post_than_post_should_be_added_to_repo() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        Post firstPost = new Post();
        firstPost.setTitle("title test");
        firstPost.setBody("ala ma kota");
        firstPost.setUser(user);

        Post secondPost = new Post();
        secondPost.setId(1L);
        secondPost.setTitle("title test");
        secondPost.setBody("ala ma kota");
        secondPost.setUser(user);

        Mockito.when(postRepository.save(firstPost)).thenReturn(secondPost);
        //when
        Post post = postServiceBasic.save(firstPost);
        //then
        assertEquals("title test", post.getTitle());
        assertEquals("ala ma kota", post.getBody());
        assertEquals(1L, post.getId());
        assertEquals(user, post.getUser());
        Mockito.verify(postRepository).save(firstPost);
    }

    @Test
    void when_update_existing_post_then_data_should_be_updated() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        Post firstPost = new Post();
        firstPost.setId(1L);
        firstPost.setTitle("title test");
        firstPost.setBody("ala ma kota");
        firstPost.setUser(user);

        Post secondPost = new Post();
        secondPost.setId(1L);
        secondPost.setTitle("title new one");
        secondPost.setBody("random body text");
        secondPost.setUser(user);

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(firstPost));
        Mockito.when(postRepository.save(any())).thenReturn(secondPost);
        //when
        Optional<Post> postUpdated = postServiceBasic.update(secondPost);
        //then
        assertEquals("title new one", postUpdated.get().getTitle());
        assertEquals("random body text", postUpdated.get().getBody());
        assertEquals(1L, postUpdated.get().getId());
        assertEquals(user, postUpdated.get().getUser());
    }

    @Test
    void when_request_find_all_than_all_posts_should_be_returned() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        Post firstPost = new Post();
        firstPost.setId(1L);
        firstPost.setTitle("title test");
        firstPost.setBody("ala ma kota");
        firstPost.setUser(user);

        Post secondPost = new Post();
        secondPost.setId(2L);
        secondPost.setTitle("title new one");
        secondPost.setBody("random body text");
        secondPost.setUser(user);

        Mockito.when(postRepository.findAll()).thenReturn(List.of(firstPost, secondPost));
        //when
        List<Post> postList = postServiceBasic.findAll();
        //then
        assertFalse(postList.isEmpty());
        assertEquals(2, postList.size());
    }

    @Test
    void when_delete_post_by_id_then_post_should_be_removed() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        Post firstPost = new Post();
        firstPost.setId(1L);
        firstPost.setTitle("title test");
        firstPost.setBody("ala ma kota");
        firstPost.setUser(user);

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(firstPost));
        //when
        postServiceBasic.delete(1L);
        //then
        Mockito.verify(postRepository).deleteById(1L);
    }

    @Test
    void when_use_getByUserId_than_all_posts_with_valid_user_id_should_be_returned() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("Sebastian Kowalski");
        secondUser.setEmail("Seba@wp.pl");

        Post firstPost = new Post();
        firstPost.setId(1L);
        firstPost.setTitle("title test");
        firstPost.setBody("ala ma kota");
        firstPost.setUser(user);

        Post secondPost = new Post();
        secondPost.setId(2L);
        secondPost.setTitle("title new one");
        secondPost.setBody("random body text");
        secondPost.setUser(user);

        Post thirdPost = new Post();
        thirdPost.setId(3L);
        thirdPost.setTitle("3 - one");
        thirdPost.setBody("txt");
        thirdPost.setUser(secondUser);

        Mockito.when(postRepository.getByUserId(1L)).thenReturn(List.of(firstPost, secondPost));
        //when
        List<Post> postList = postServiceBasic.getByUserId(1L);
        //then
        assertEquals(2, postList.size());
    }
}