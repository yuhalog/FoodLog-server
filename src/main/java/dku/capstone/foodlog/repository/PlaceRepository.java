package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {

    Place findByKakaoPlaceId(Long kakaoPlaceId);
}
