package app.comment.web.resources;

import app.comment.repository.Comment;
import app.organisation.repository.Organisation;
import app.organisation.web.resources.OrganisationDTO;
import app.post.repository.Post;
import app.post.web.resources.PostDTO;
import app.user.repository.User;
import app.user.web.resources.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private CommentMapper commentMapper = new CommentMapperImpl();

    @Test
    void when_use_getDTO_on_comment_than_should_return_commentDTO() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("some organisation");
        organisation.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("jan@wp.pl");
        user.setOrganisation(organisation);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("title test");
        post.setBody("ala ma kota");
        post.setUser(user);
        post.setCreateAt(Instant.now());

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBody("sample text");
        comment.setPost(post);
        comment.setCreateAt(Instant.now());
        //when
        CommentDTO commentDTO = commentMapper.mapToCommentDTO(comment);
        //then
        assertEquals(1, commentDTO.getId());
        assertNull(commentDTO.getBody());
        assertNotNull(commentDTO.getCreateAt());
        assertEquals(1, commentDTO.getUser().getId());
        assertEquals("Jan Kowalski", commentDTO.getUser().getUsername());
        assertNull(commentDTO.getUser().getRoles());
        assertNull(commentDTO.getUser().getOrganisation());
        assertNull(commentDTO.getUser().getEmail());
        assertEquals(1, commentDTO.getPost().getId());
        assertNull(commentDTO.getPost().getBody());
        assertNull(commentDTO.getPost().getUser());
        assertNull(commentDTO.getPost().getTitle());
        assertNull(commentDTO.getPost().getComments());
        assertNull(commentDTO.getPost().getCreateAt());

    }

    @Test
    void when_use_getModel_on_commentDTO_than_should_return_comment() {
        //given
        OrganisationDTO organisationDTO = new OrganisationDTO();
        organisationDTO.setName("some organisation");
        organisationDTO.setId(1L);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Jan Kowalski");
        userDTO.setEmail("jan@wp.pl");
        userDTO.setOrganisation(organisationDTO);

        PostDTO postDTO = new PostDTO();
        postDTO.setId(1L);
        postDTO.setTitle("title test");
        postDTO.setBody("ala ma kota");
        postDTO.setUser(userDTO);
        postDTO.setCreateAt(Instant.now());

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setUser(userDTO);
        commentDTO.setBody("sample text");
        commentDTO.setPost(postDTO);
        commentDTO.setCreateAt(Instant.now());
        //when
        Comment comment = commentMapper.getModel(commentDTO);
        //then
        assertEquals(1, comment.getId());
        assertEquals("sample text", comment.getBody());
        assertNotNull(comment.getCreateAt());
        assertEquals(1, comment.getUser().getId());
        assertEquals("Jan Kowalski", comment.getUser().getUsername());
        assertTrue(comment.getUser().getRoles().isEmpty());
        assertNull(comment.getUser().getOrganisation());
        assertNull(comment.getUser().getEmail());
        assertEquals(1, comment.getPost().getId());
        assertNull(comment.getPost().getBody());
        assertNull(comment.getPost().getUser());
        assertNull(comment.getPost().getTitle());
        assertTrue(comment.getPost().getComments().isEmpty());
        assertNull(comment.getPost().getCreateAt());

    }

}