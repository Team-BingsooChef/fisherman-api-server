package my.fisherman.fisherman.smelt.repository;

import my.fisherman.fisherman.smelt.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
