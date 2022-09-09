package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.dto.request.KakaoPlaceRequest;
import dku.capstone.foodlog.dto.request.PostRequest;
import dku.capstone.foodlog.dto.response.KakaoPlaceResponse;

public interface PlaceService {

    Place checkPlaceInDb(KakaoPlaceRequest kakaoPlaceRequest);
}
