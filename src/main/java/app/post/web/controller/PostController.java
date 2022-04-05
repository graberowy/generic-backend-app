package app.post.web.controller;

import app.generic.web.controller.GenericControllerImpl;
import app.post.repository.Post;
import app.post.service.PostServiceBasic;
import app.post.web.resources.PostDTO;
import app.post.web.resources.PostMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
/**
 * This class if for cover CRUD operations on post
 *
 * @author pawel.jankowski
 */
@RestController
@RequestMapping("/api/v1")
public class PostController extends GenericControllerImpl<PostDTO, Post, Long> {

    private final PostServiceBasic postServiceBasic;
    private final PostMapper postMapper;

    /**
     * This is constructor for use generic operations on specific type
     * @param postServiceBasic post service
     * @param postMapper post object mapper
     */
    public PostController(PostServiceBasic postServiceBasic, PostMapper postMapper) {
        super(postServiceBasic, postMapper);
        this.postServiceBasic = postServiceBasic;
        this.postMapper = postMapper;
    }

    /**
     * This method is for get all post records
     * @return list of posts
     */
    @Override
    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAll() {
        return ResponseEntity.ok(postServiceBasic.findAll().stream()
                .map(postMapper::mapToPostDTOBasic)
                .collect(Collectors.toList()));
    }

    @Override
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    @PostMapping("/posts")
    public ResponseEntity<PostDTO> saveNew(@RequestBody PostDTO dto) {
        return super.saveNew(dto);
    }

    @Override
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDTO> updateRecord(@PathVariable Long id, @RequestBody PostDTO dto) {
        return super.updateRecord(id, dto);
    }

    @Override
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        return super.deleteRecord(id);
    }

    /**
     * This method is for get all posts related to same user
     * @param id of user
     * @return list of posts
     */
    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<PostDTO>> getByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(postServiceBasic.getByUserId(id).stream()
                .map(postMapper::mapToPostDTOBasic)
                .collect(Collectors.toList()));

    }
}
