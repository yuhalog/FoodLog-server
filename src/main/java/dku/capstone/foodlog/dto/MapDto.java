package dku.capstone.foodlog.dto;

import dku.capstone.foodlog.constant.FoodCategory;
import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MapDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Response {

        private Long placeId;

        private Long placePostId;

        private Long postId;

        private String name;

        private String category;

        private Double latitude;

        private Double longitude;

        public static Response entityToDto(Place place) {
            return Response.builder()
                    .placeId(place.getId())
                    .placePostId(place.getPlacePost().getId())
                    .postId(place.getPostList().get(0).getId())
                    .name(place.getName())
                    .category(place.getCategory().getValue())
                    .latitude(place.getLatitude())
                    .longitude(place.getLongitude())
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Request {

        private Double longitude;

        private Double latitude;

        private Double longitudeDelta;

        private Double latitudeDelta;

        private List<FoodPurpose> purposeList;

        private List<FoodCategory> categoryList;

        private Integer rating;

    }

}
