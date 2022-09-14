package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.PlacePost;
import dku.capstone.foodlog.dto.PlacePostDto;
import dku.capstone.foodlog.dto.PostDto;

public interface PlacePostService {

    void addPlacePost(PlacePost placePost);

    void removePlacePost(PlacePost placePost);

    PlacePost savePlacePost(Place place, PostDto.Request postRequest);

    PlacePostDto.Detail getPlacePostDetail(Long placePostId);

}
