package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.dto.request.PostRequest;

public interface PlaceService {

    Place checkPlaceInDb(PostRequest postRequest);
}
