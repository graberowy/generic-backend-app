package app.post.web.resources;

import app.comment.repository.Comment;
import app.comment.web.resources.CommentDTO;
import app.organisation.repository.Organisation;
import app.organisation.web.resources.OrganisationDTO;
import app.post.repository.Post;
import app.user.repository.User;
import app.user.web.resources.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {

    private PostMapper postMapper = new PostMapperImpl();

    @Test
    void when_use_getDTO_on_Post_than_PostDTO_should_be_returned() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("some organisation");
        organisation.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("jan@wp.pl");
        user.setOrganisation(organisation);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("sample text");
        comment.setCreateAt(Instant.now());

        Comment secondComment = new Comment();
        secondComment.setId(2L);
        secondComment.setUser(user);
        secondComment.setBody("sample text2");
        secondComment.setCreateAt(Instant.now());

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);
        post.setComments(List.of(comment, secondComment));
        post.setCreateAt(Instant.now());
        //when
        PostDTO postDTO = postMapper.getDTO(post);
        //then
        assertEquals(1, postDTO.getId());
        assertEquals("title test", postDTO.getTitle());
        assertEquals("ala ma kota", postDTO.getBody());
        assertEquals("Jan Kowalski", postDTO.getUser().getUsername());
        assertEquals(1, postDTO.getUser().getId());
        assertNull(postDTO.getUser().getEmail());
        assertNull(postDTO.getUser().getOrganisation());
        assertNull(postDTO.getUser().getRoles());
        assertEquals(2, postDTO.getComments().size());
        assertEquals(1, postDTO.getComments().get(0).getId());
        assertEquals(2, postDTO.getComments().get(1).getId());
        assertEquals("sample text", postDTO.getComments().get(0).getBody());
        assertEquals("sample text2", postDTO.getComments().get(1).getBody());
        assertEquals(1, postDTO.getComments().get(0).getUser().getId());
        assertEquals(1, postDTO.getComments().get(1).getUser().getId());
        assertEquals("Jan Kowalski", postDTO.getComments().get(0).getUser().getUsername());
        assertEquals("Jan Kowalski", postDTO.getComments().get(1).getUser().getUsername());
        assertNull(postDTO.getComments().get(0).getUser().getEmail());
        assertNull(postDTO.getComments().get(0).getUser().getOrganisation());
        assertNull(postDTO.getComments().get(0).getUser().getRoles());
        assertNull(postDTO.getComments().get(1).getUser().getEmail());
        assertNull(postDTO.getComments().get(1).getUser().getOrganisation());
        assertNull(postDTO.getComments().get(1).getUser().getRoles());
        assertNotNull(postDTO.getComments().get(0).getCreateAt());
        assertNotNull(postDTO.getComments().get(1).getCreateAt());
        assertNotNull(postDTO.getCreateAt());


    }

    @Test
    void when_use_getModel_on_PostDTO_than_Post_should_be_returned() {
        //given
        OrganisationDTO organisationDTO = new OrganisationDTO();
        organisationDTO.setName("some organisation");
        organisationDTO.setId(1L);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Jan Kowalski");
        userDTO.setEmail("jan@wp.pl");
        userDTO.setOrganisation(organisationDTO);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setUser(userDTO);
        commentDTO.setBody("sample text");
        commentDTO.setCreateAt(Instant.now());

        CommentDTO secondCommentDTO = new CommentDTO();
        secondCommentDTO.setId(2L);
        secondCommentDTO.setUser(userDTO);
        secondCommentDTO.setBody("sample text2");
        secondCommentDTO.setCreateAt(Instant.now());

        PostDTO postDTO = new PostDTO();
        postDTO.setId(1L);
        postDTO.setTitle("title test");
        postDTO.setBody("ala ma kota");
        postDTO.setUser(userDTO);
        postDTO.setComments(List.of(commentDTO, secondCommentDTO));
        postDTO.setCreateAt(Instant.now());
        //when
        Post post = postMapper.getModel(postDTO);
        //then
        assertEquals(1, post.getId());
        assertEquals("title test", post.getTitle());
        assertEquals("ala ma kota", post.getBody());
        assertEquals("Jan Kowalski", post.getUser().getUsername());
        assertEquals(1, post.getUser().getId());
        assertNull(post.getUser().getEmail());
        assertNull(post.getUser().getOrganisation());
        assertTrue(post.getUser().getRoles().isEmpty());
        assertEquals(2, post.getComments().size());
        assertEquals(1, post.getComments().get(0).getId());
        assertEquals(2, post.getComments().get(1).getId());
        assertEquals("sample text", post.getComments().get(0).getBody());
        assertEquals("sample text2", post.getComments().get(1).getBody());
        assertEquals(1, post.getComments().get(0).getUser().getId());
        assertEquals(1, post.getComments().get(1).getUser().getId());
        assertEquals("Jan Kowalski", post.getComments().get(0).getUser().getUsername());
        assertEquals("Jan Kowalski", post.getComments().get(1).getUser().getUsername());
        assertNull(post.getComments().get(0).getUser().getEmail());
        assertNull(post.getComments().get(0).getUser().getOrganisation());
        assertTrue(post.getComments().get(0).getUser().getRoles().isEmpty());
        assertNull(post.getComments().get(1).getUser().getEmail());
        assertNull(post.getComments().get(1).getUser().getOrganisation());
        assertTrue(post.getComments().get(1).getUser().getRoles().isEmpty());
        assertNotNull(post.getComments().get(0).getCreateAt());
        assertNotNull(post.getComments().get(1).getCreateAt());
        assertNotNull(post.getCreateAt());
    }

    @Test
    void when_use_mapToPostDTOBasic_on_Post_than_PostDTO_with_basic_mapping_should_be_returned() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("some organisation");
        organisation.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("jan@wp.pl");
        user.setOrganisation(organisation);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("sample text");
        comment.setCreateAt(Instant.now());

        Comment secondComment = new Comment();
        secondComment.setId(2L);
        secondComment.setUser(user);
        secondComment.setBody("sample text2");
        secondComment.setCreateAt(Instant.now());

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);
        post.setComments(List.of(comment, secondComment));
        post.setCreateAt(Instant.now());
        //when
        PostDTO postDTO = postMapper.mapToPostDTOBasic(post);
        //then
        assertEquals(1, postDTO.getId());
        assertEquals("title test", postDTO.getTitle());
        assertNull(postDTO.getBody());
        assertEquals("Jan Kowalski", postDTO.getUser().getUsername());
        assertEquals(1, postDTO.getUser().getId());
        assertNull(postDTO.getUser().getEmail());
        assertNull(postDTO.getUser().getOrganisation());
        assertNull(postDTO.getUser().getRoles());
        assertNull(postDTO.getComments());
        assertNotNull(postDTO.getCreateAt());
    }

    @Test
    void when_use_mapToPostBasic_on_PostDTO_than_Post_with_basic_mapping_should_be_returned() {
        //given
        OrganisationDTO organisationDTO = new OrganisationDTO();
        organisationDTO.setName("some organisation");
        organisationDTO.setId(1L);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Jan Kowalski");
        userDTO.setEmail("jan@wp.pl");
        userDTO.setOrganisation(organisationDTO);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setUser(userDTO);
        commentDTO.setBody("sample text");
        commentDTO.setCreateAt(Instant.now());

        CommentDTO secondCommentDTO = new CommentDTO();
        secondCommentDTO.setId(2L);
        secondCommentDTO.setUser(userDTO);
        secondCommentDTO.setBody("sample text2");
        secondCommentDTO.setCreateAt(Instant.now());

        PostDTO postDTO = new PostDTO();
        postDTO.setId(1L);
        postDTO.setTitle("title test");
        postDTO.setBody("ala ma kota");
        postDTO.setUser(userDTO);
        postDTO.setComments(List.of(commentDTO, secondCommentDTO));
        postDTO.setCreateAt(Instant.now());
        //when
        Post post = postMapper.mapToPostBasic(postDTO);
        //then
        assertEquals(1, post.getId());
        assertEquals("title test", post.getTitle());
        assertNull(post.getBody());
        assertEquals("Jan Kowalski", post.getUser().getUsername());
        assertEquals(1, post.getUser().getId());
        assertNull(post.getUser().getEmail());
        assertNull(post.getUser().getOrganisation());
        assertTrue(post.getUser().getRoles().isEmpty());
        assertTrue(post.getComments().isEmpty());
        assertNotNull(post.getCreateAt());
    }

}