package app.post.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "user")
    List<Post> getByUserId(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "user")
    List<Post> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"user", "comments"})
    Optional<Post> findById(Long id);
}
