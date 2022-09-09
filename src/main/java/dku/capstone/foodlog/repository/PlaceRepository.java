package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Place findByKakaoPlaceId(Long kakaoPlaceId);
}
