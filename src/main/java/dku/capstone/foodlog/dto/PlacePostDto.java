package dku.capstone.foodlog.dto;

import dku.capstone.foodlog.domain.PlacePost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class PlacePostDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Detail {

        private Long placePostid;

        private Float averageRating;

        private Long postCount;

        private PlaceDto.Response place;

        private List<PostDto.Summary> contents;

        public Detail(PlacePost placePost) {
            this.placePostid = placePost.getId();
            this.averageRating = placePost.getAverageRating();
            this.postCount = placePost.getPostCount();
            this.place = new PlaceDto.Response(placePost.getPlace());
            this.contents = placePost.getPlace().getPostList().stream()
                    .map(entity -> new PostDto.Summary(entity))
                    .collect(Collectors.toList());
        }
    }
}
