package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
