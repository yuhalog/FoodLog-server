package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.PlacePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacePostRepository extends JpaRepository<PlacePost, Long> {

}
