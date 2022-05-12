package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
