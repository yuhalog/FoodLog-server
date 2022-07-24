package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Place findByLatitudeAndLongitude(Double latitude, Double longitude);
}
