package dku.capstone.foodlog.dto;

import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.PostPicture;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class PostDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Summary {

        private Long postId;

        private String picture;

        private String review;

        public Summary(Post post) {
            this.postId = post.getId();
            this.picture = post.getPictureList().get(0).getPictureUrl();
            this.review = post.getReview();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Response {

        @ApiModelProperty(example = "23")
        private Long postId;

        @ApiModelProperty(example = "test1")
        private String member;

        @ApiModelProperty(dataType = "List", example = "[https://foodlogstorage.s3.ap-northeast-2.amazonaws.com/d857688e-0ed8-4200-8aa0-5b770e30e016.jpeg]")
        private List<String> pictures;

        @ApiModelProperty(example = "3")
        private Integer rating;

        @ApiModelProperty(example = "정말 맛있어요!")
        private String review;

        @ApiModelProperty(example = "가족")
        private FoodPurpose purpose;

        @ApiModelProperty(example = "2022-09-11")
        private String date;

        private PlaceDto.Response place;

        public Response(Post post) {
            this.postId = post.getId();
            this.member = post.getMember().getUsername();
            this.pictures = post.getPictureList().stream().map(PostPicture::getPictureUrl).collect(Collectors.toList());
            this.rating = post.getRating();
            this.review = post.getReview();
            this.purpose = post.getPurpose();
            this.date = post.getDate().toString();
            this.place = new PlaceDto.Response(post.getPlace());
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Request {

        @ApiModelProperty(value="회원의 id", example = "1")
        private Long memberId;

        @ApiModelProperty(value="게사물의 별점", example = "3")
        private Integer rating;

        private String review;

        private FoodPurpose purpose;

        private String date;

        private PlaceDto.Request place;
    }
}
