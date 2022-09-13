package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.dto.MapDto;

import java.util.List;

public interface PlaceRepositoryCustom {

    List<Place> searchPlace(MapDto.Request mapRequest);
}
