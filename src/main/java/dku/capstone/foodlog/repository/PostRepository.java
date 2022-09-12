package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);

    @Query("select avg(coalesce(p.rating, 0)) from Post p " +
            "where p.place.id = :id")
    Float getAverageRating(@Param("id") Long id);

    List<Post> findAllByOrderByIdDesc(Pageable page);

    List<Post> findByIdLessThanOrderByIdDesc(Long id, Pageable page);

    Boolean existsByIdLessThan(Long id);
}
