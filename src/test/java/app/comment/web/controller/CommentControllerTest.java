package app.comment.web.controller;

import app.comment.repository.Comment;
import app.comment.service.CommentServiceBasic;
import app.comment.web.resources.CommentMapperImpl;
import app.organisation.repository.Organisation;
import app.post.repository.Post;
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
import org.springframework.web.util.NestedServletException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
@ContextConfiguration(classes = {CommentMapperImpl.class, CommentController.class})
class CommentControllerTest {

    @MockBean
    private CommentServiceBasic commentServiceBasic;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_send_get_request_by_id_with_existing_comment_then_comment_should_be_returned() throws Exception {
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

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("test1 text");
        comment.setPost(post);
        comment.setCreateAt(Instant.now());


        Mockito.when(commentServiceBasic.findById(1L)).thenReturn(Optional.of(comment));
        //when
        //then
        mockMvc.perform(get("/api/v1/comments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("test1 text")))
                .andExpect(jsonPath("$.user.id", equalTo(1)))
                .andExpect(jsonPath("$.user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.user.roles").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.organisation").doesNotExist())
                .andExpect(jsonPath("$.post.id", equalTo(1)))
                .andExpect(jsonPath("$.post.title").doesNotExist())
                .andExpect(jsonPath("$.post.body").doesNotExist())
                .andExpect(jsonPath("$.post.user").doesNotExist())
                .andExpect(jsonPath("$.createAt", equalTo("" + comment.getCreateAt() + "")));
    }

    @Test
    void when_send_get_request_by_id_which_is_not_exist_then_not_found_error_should_be_returned() throws Exception {
        //given
        Mockito.when(commentServiceBasic.findById(1L)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(get("/api/v1/comments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_post_request_which_has_no_existing_comment_then_comment_should_be_returned_as_response() throws Exception {
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

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("test1 text");
        comment.setPost(post);

        Mockito.when(commentServiceBasic.save(any())).thenReturn(comment);
        //when
        //then
        mockMvc.perform(post("/api/v1/comments").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"body\": \"test1 text\",\n" +
                                "  \"user\": { \"id\": 1 },\n" +
                                "  \"post\": { \"id\": 1 }\n" +
                                "}"))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("test1 text")))
                .andExpect(jsonPath("$.user.id", equalTo(1)))
                .andExpect(jsonPath("$.user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.user.roles").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.organisation").doesNotExist())
                .andExpect(jsonPath("$.post.id", equalTo(1)))
                .andExpect(jsonPath("$.post.title").doesNotExist())
                .andExpect(jsonPath("$.post.body").doesNotExist())
                .andExpect(jsonPath("$.post.user").doesNotExist());
    }

    @Test
    void when_send_comment_get_request_then_status_503_should_be_returned() throws Exception {
        //given
        //when
        //then
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/api/v1/comments").contentType(MediaType.APPLICATION_JSON));
        });
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

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("test1 text");
        comment.setPost(post);;

        Mockito.when(commentServiceBasic.findById(1L)).thenReturn(Optional.of(comment));

        //when
        commentServiceBasic.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/comments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void when_send_delete_request_which_is_not_exist_id_then_not_found_error_should_be_returned() throws Exception {
        //given
        //when
        commentServiceBasic.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/comments/1").contentType(MediaType.APPLICATION_JSON))
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

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("test1 text");
        comment.setPost(post);

        Comment secondComment = new Comment();
        secondComment.setId(1L);
        secondComment.setUser(user);
        secondComment.setBody("body updated !!!");
        secondComment.setPost(post);

        Mockito.when(commentServiceBasic.update(any())).thenReturn(Optional.of(secondComment));
        //when
        //then
        mockMvc.perform(put("/api/v1/comments/1")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"body\": \"body updated !!!\",\n" +
                                "  \"user\": { \"id\": 1 },\n" +
                                "  \"post\": { \"id\": 1 }\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("body updated !!!")))
                .andExpect(jsonPath("$.user.id", equalTo(1)))
                .andExpect(jsonPath("$.user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.user.roles").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.organisation").doesNotExist())
                .andExpect(jsonPath("$.post.id", equalTo(1)))
                .andExpect(jsonPath("$.post.title").doesNotExist())
                .andExpect(jsonPath("$.post.body").doesNotExist())
                .andExpect(jsonPath("$.post.user").doesNotExist());
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

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("test1 text");
        comment.setPost(post);

        Comment secondComment = new Comment();
        secondComment.setId(2L);
        secondComment.setUser(user);
        secondComment.setBody("body updated !!!");
        secondComment.setPost(post);

        Mockito.when(commentServiceBasic.update(secondComment)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(put("/api/v1/users/1")
                        .content("{\n" +
                                "  \"id\": 2,\n" +
                                "  \"body\": \"body updated !!!\",\n" +
                                "  \"user\": { \"id\": 1 },\n" +
                                "  \"post\": { \"id\": 1 }\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_sent_getByPostId_then_all_comments_with_valid_post_id_should_be_returned() throws Exception {
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

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("test1 text");
        comment.setPost(post);

        Comment secondComment = new Comment();
        secondComment.setId(2L);
        secondComment.setUser(user);
        secondComment.setBody("body updated !!!");
        secondComment.setPost(post);

        Mockito.when(commentServiceBasic.findByPostId(1L)).thenReturn(List.of(comment, secondComment));
        //when
        //then
        mockMvc.perform(get("/api/v1/post/1/comments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[0].user.id", equalTo(1)))
                .andExpect(jsonPath("$[1].user.id", equalTo(1)))
                .andExpect(jsonPath("$[0].user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$[0].user.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$[0].post.id", equalTo(1)))
                .andExpect(jsonPath("$[0].post.id", equalTo(1)))
                .andExpect(jsonPath("$.user.roles").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.organisation").doesNotExist());
    }
}