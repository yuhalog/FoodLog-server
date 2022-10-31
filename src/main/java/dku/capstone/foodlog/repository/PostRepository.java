package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Optional<Post> findById(Long postId);

    @Query("SELECT AVG(COALESCE(p.rating, 0)) FROM Post p " +
            "WHERE p.place.id = :id")
    Float getAverageRating(@Param("id") Long placeId);

    @Query("SELECT p.purpose FROM Post p " +
            "WHERE p.place.id = :id " +
            "GROUP BY p.purpose " +
            "ORDER BY COUNT (p) DESC")
    List<String> findWithPagingByPurpose(@Param("id") Long placeId);
}
