package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.dto.request.PlaceRequest;

public interface PlaceService {

    Place checkPlaceInDb(PlaceRequest placeRequest, Integer rating);
}
