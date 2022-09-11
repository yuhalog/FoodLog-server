package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.dto.PostDto;

public interface PlaceService {

    Place checkPlaceInDb(PostDto.Request postRequest);
}
