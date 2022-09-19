package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.dto.MapDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaceRepositoryCustom {

    List<Place> searchPlace(MapDto.Filter mapRequest);

    Page<Place> getPageSearchPlaceByName(MapDto.Search mapSearch, Pageable pageable);

    Page<Place> getPageSearchPlaceByAddress(MapDto.Search mapSearch, Pageable pageable);

}
