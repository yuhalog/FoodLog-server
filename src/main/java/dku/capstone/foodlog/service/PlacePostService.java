package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.PlacePost;
import dku.capstone.foodlog.dto.PlacePostDto;

public interface PlacePostService {

    void setAverageRating(PlacePost placePost);

    PlacePost savePlacePost(Place place, Integer rating);

    PlacePostDto.Detail getPlacePostDetail(Long placePostId);

}
