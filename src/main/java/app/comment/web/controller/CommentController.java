package app.comment.web.controller;

import app.comment.repository.Comment;
import app.comment.service.CommentServiceBasic;
import app.comment.web.resources.CommentDTO;
import app.comment.web.resources.CommentMapper;
import app.generic.web.controller.GenericControllerImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CommentController extends GenericControllerImpl<CommentDTO, Comment, Long> {
    private final CommentServiceBasic commentServiceBasic;
    private final CommentMapper commentMapper;


    public CommentController(CommentServiceBasic commentServiceBasic, CommentMapper commentMapper) {
        super(commentServiceBasic, commentMapper);
        this.commentServiceBasic = commentServiceBasic;
        this.commentMapper = commentMapper;
    }

    @Override
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> saveNew(@RequestBody CommentDTO dto) {
        return super.saveNew(dto);
    }

    @Override
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> updateRecord(@PathVariable Long id, @RequestBody CommentDTO dto) {
        return super.updateRecord(id, dto);
    }

    @Override
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        return super.deleteRecord(id);
    }

    @GetMapping("/post/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getByPostId(@PathVariable Long id) {
        return ResponseEntity.ok(commentServiceBasic.findByPostId(id).stream()
                .map(commentMapper::mapToCommentDTO)
                .collect(Collectors.toList()));

    }
}
