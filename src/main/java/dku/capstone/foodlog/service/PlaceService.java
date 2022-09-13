package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.dto.MapDto;
import dku.capstone.foodlog.dto.PostDto;

import java.util.List;

public interface PlaceService {

    Place checkPlaceInDb(PostDto.Request postRequest);

    List<MapDto.Response> searchPlaceWithFilter(MapDto.Request mapRequest);
}
