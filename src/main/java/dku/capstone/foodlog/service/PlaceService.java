package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.response.PlacePostResponse;
import dku.capstone.foodlog.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    /**
     * 중간 게시물 조회
     */
    public PlacePostResponse getPlacePost(Long placeId) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NoSuchElementException("장소가 없습니다."));

        List<PlacePostResponse.PictureReview> pictureReviews = new ArrayList<>();

        for (Post post : place.getPostList()) {

            String picture = post.getPictureList().get(0).getPictureUrl();
            String review = post.getReview();

            PlacePostResponse.PictureReview pictureReview = PlacePostResponse.PictureReview.builder()
                    .pictureUrl(picture)
                    .review(review)
                    .build();

            pictureReviews.add(pictureReview);
        }

        PlacePostResponse response = PlacePostResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .rating(place.getAverage_rating())
                .pictureReviews(pictureReviews)
                .build();

        return response;
    }
}