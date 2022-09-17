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

        private Double averageRating;

        private Double latitude;

        private Double longitude;

        public Response(Long placeId, Long placePostId, String name, String category, Double averageRating, Double latitude, Double longitude) {
            this.placeId = placeId;
            this.placePostId = placePostId;
            this.name = name;
            this.category = category;
            this.averageRating = averageRating;
            this.latitude = latitude;
            this.longitude = longitude;
        }

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
    public static class Search {

        private Double longitude;

        private Double latitude;

        private Double longitudeDelta;

        private Double latitudeDelta;

        private String query;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Filter {

        private Double longitude;

        private Double latitude;

        private Double longitudeDelta;

        private Double latitudeDelta;

        private List<FoodPurpose> purposeList;

        private List<FoodCategory> categoryList;

        private Integer rating;

    }

}
