package app.post.web.controller;

import app.comment.repository.Comment;
import app.organisation.repository.Organisation;
import app.post.repository.Post;
import app.post.service.PostServiceBasic;
import app.post.web.resources.PostMapperImpl;
import app.user.repository.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@ContextConfiguration(classes = {PostMapperImpl.class, PostController.class})
class PostControllerTest {

    @MockBean
    private PostServiceBasic postServiceBasic;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_send_get_request_by_id_with_existing_post_then_post_should_be_returned() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("test1 text");
        comment.setCreateAt(Instant.now());

        Comment secondComment = new Comment();
        secondComment.setId(2L);
        secondComment.setUser(user);
        secondComment.setBody("test2 text");
        secondComment.setCreateAt(Instant.now());

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);
        post.setComments(List.of(comment, secondComment));
        post.setCreateAt(Instant.now());


        Mockito.when(postServiceBasic.findById(1L)).thenReturn(Optional.of(post));
        //when
        //then
        mockMvc.perform(get("/api/v1/posts/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("title test")))
                .andExpect(jsonPath("$.body", equalTo("ala ma kota")))
                .andExpect(jsonPath("$.user.id", equalTo(1)))
                .andExpect(jsonPath("$.user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.user.roles").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.organisation").doesNotExist())
                .andExpect(jsonPath("$.comments", hasSize(2)))
                .andExpect(jsonPath("$.comments[?(@.id == 1)].id", equalTo(List.of(1))))
                .andExpect(jsonPath("$.comments[?(@.id == 1)].body", equalTo(List.of("test1 text"))))
                .andExpect(jsonPath("$.comments[?(@.id == 1)].createAt", equalTo(List.of("" + post.getCreateAt() + ""))))
                .andExpect(jsonPath("$.comments[?(@.id == 1)].user.id", equalTo(List.of(1))))
                .andExpect(jsonPath("$.comments[?(@.id == 1)].user.username", equalTo(List.of("Jan Kowalski"))))
                .andExpect(jsonPath("$.comments[?(@.id == 2)].id", equalTo(List.of(2))))
                .andExpect(jsonPath("$.comments[?(@.id == 2)].body", equalTo(List.of("test2 text"))))
                .andExpect(jsonPath("$.comments[?(@.id == 2)].createAt", equalTo(List.of("" + post.getCreateAt() + ""))))
                .andExpect(jsonPath("$.comments[?(@.id == 2)].user.id", equalTo(List.of(1))))
                .andExpect(jsonPath("$.comments[?(@.id == 2)].user.username", equalTo(List.of("Jan Kowalski"))));
    }

    @Test
    void when_send_get_request_by_id_which_is_not_exist_then_not_found_error_should_be_returned() throws Exception {
        //given
        Mockito.when(postServiceBasic.findById(1L)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(get("/api/v1/posts/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_post_request_which_has_no_existing_post_then_post_should_be_returned_as_response() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);


        Mockito.when(postServiceBasic.save(any())).thenReturn(post);
        //when
        //then
        mockMvc.perform(post("/api/v1/posts")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"title\": \"title test\",\n" +
                                "  \"body\": \"ala ma kota\",\n" +
                                "  \"user\": { \"id\": 1, \"username\": \"Jan Kowalski\", \"email\": \"Jan@wp.pl\", \"roles\": [] },\n" +
                                "  \"comments\": []\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("title test")))
                .andExpect(jsonPath("$.body", equalTo("ala ma kota")))
                .andExpect(jsonPath("$.user.id", equalTo(1)))
                .andExpect(jsonPath("$.user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.user.roles").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.organisation").doesNotExist())
                .andExpect(jsonPath("$.comments").isEmpty());
    }

    @Test
    void when_send_post_get_request_then_list_posts_should_be_returned() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Post secondPost = new Post();
        secondPost.setId(2L);
        secondPost.setTitle("title test2");
        secondPost.setBody("ala ma kota");
        secondPost.setUser(user);

        Mockito.when(postServiceBasic.findAll()).thenReturn(Arrays.asList(post, secondPost));
        //when
        //then
        mockMvc.perform(get("/api/v1/posts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[0].title", equalTo("title test")))
                .andExpect(jsonPath("$[1].title", equalTo("title test2")))
                .andExpect(jsonPath("$[0].user.id", equalTo(1)))
                .andExpect(jsonPath("$[1].user.id", equalTo(1)))
                .andExpect(jsonPath("$[0].user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$[0].user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.user.roles").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.organisation").doesNotExist());
    }

    @Test
    void when_send_delete_request_which_existing_id_then_record_should_be_removed() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Mockito.when(postServiceBasic.findById(1L)).thenReturn(Optional.of(post));

        //when
        postServiceBasic.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/posts/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void when_send_delete_request_which_is_not_exist_id_then_not_found_error_should_be_returned() throws Exception {
        //given
        //when
        postServiceBasic.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/posts/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_put_request_which_existing_id_then_data_should_be_updated() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Post secondPost = new Post();
        secondPost.setId(1L);
        secondPost.setTitle("title test2");
        secondPost.setBody("body updated !!!");
        secondPost.setUser(user);

        Mockito.when(postServiceBasic.update(any())).thenReturn(Optional.of(secondPost));
        //when
        //then
        mockMvc.perform(put("/api/v1/posts/1")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"title\": \"title test2\",\n" +
                                "  \"body\": \"body updated !!!\",\n" +
                                "  \"user\": { \"id\": 1, \"username\": \"Jan Kowalski\", \"email\": \"Jan@wp.pl\", \"roles\": [] },\n" +
                                "  \"comments\": []\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("title test2")))
                .andExpect(jsonPath("$.body", equalTo("body updated !!!")))
                .andExpect(jsonPath("$.user.id", equalTo(1)))
                .andExpect(jsonPath("$.user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.user.roles").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.organisation").doesNotExist())
                .andExpect(jsonPath("$.comments").isEmpty());
    }

    @Test
    void when_send_put_request_which_not_existing_id_then_not_found_error_should_be_returned() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Post secondPost = new Post();
        secondPost.setId(2L);
        secondPost.setTitle("title test2");
        secondPost.setBody("body updated !!!");
        secondPost.setUser(user);

        Mockito.when(postServiceBasic.update(secondPost)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(put("/api/v1/users/1")
                        .content("{\n" +
                                "  \"id\": 2,\n" +
                                "  \"title\": \"title test2\",\n" +
                                "  \"body\": \"body updated !!!\",\n" +
                                "  \"user\": { \"id\": 1, \"username\": \"Jan Kowalski\", \"email\": \"Jan@wp.pl\", \"roles\": [] },\n" +
                                "  \"comments\": []\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_sent_getByUserId_then_all_posts_with_valid_user_id_should_be_returned() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);

        User secondUser = new User();
        secondUser.setUsername("Sebastian Kowalski");
        secondUser.setEmail("Seba@wp.pl");
        secondUser.setId(2L);
        secondUser.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);

        Post secondPost = new Post();
        secondPost.setId(2L);
        secondPost.setTitle("title test2");
        secondPost.setBody("body updated !!!");
        secondPost.setUser(user);

        Post thirdPost = new Post();
        thirdPost.setId(3L);
        thirdPost.setTitle("title test3");
        thirdPost.setBody("body updated test 3 !!!");
        thirdPost.setUser(secondUser);

        Mockito.when(postServiceBasic.getByUserId(1L)).thenReturn(List.of(post, secondPost));
        //when
        //then
        mockMvc.perform(get("/api/v1/users/1/posts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[0].title", equalTo("title test")))
                .andExpect(jsonPath("$[1].title", equalTo("title test2")))
                .andExpect(jsonPath("$[0].user.id", equalTo(1)))
                .andExpect(jsonPath("$[1].user.id", equalTo(1)))
                .andExpect(jsonPath("$[0].user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$[0].user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.user.roles").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.organisation").doesNotExist());
    }



}