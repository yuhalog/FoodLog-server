package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.PostPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPictureRepository extends JpaRepository<PostPicture, Long> {
}
