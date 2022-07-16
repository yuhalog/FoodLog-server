package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post getById(Long postId);

    List<Post> findAllByOrderByIdDesc(Pageable page);

    List<Post> findByIdLessThanOrderByIdDesc(Long id, Pageable page);

    Boolean existsByIdLessThan(Long id);
}
