package dku.capstone.foodlog.dto.request;

import dku.capstone.foodlog.constant.FoodCategory;
import dku.capstone.foodlog.constant.FoodPurpose;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.PostPicture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostFormDto {

    private Long memberId;

    private List<String> pictureList;

    private Integer rating;

    private String review;

    private FoodCategory category;

    private FoodPurpose purpose;

    //place의 위도, 경도 정보
    private Double latitude;

    private Double longitude;

    private String placeName;

    private String placeAddress;

    private String date;

    public List<String> pictureList(List<PostPicture> postPictureList) {
        List<String> pictureList = new ArrayList<>();

        for (PostPicture postPicture : postPictureList) {
            pictureList.add(postPicture.getPictureUrl());
        }
        return pictureList;
    }

    public PostFormDto(Post post, List<String> pictureList) {
        this.pictureList = pictureList;
        this.rating = post.getRating();
        this.review = post.getReview();
        this.category = post.getCategory();
        this.purpose = post.getPurpose();
        this.placeName = post.getPlace().getName();
        this.placeAddress = post.getPlace().getAddress();
        this.latitude = post.getPlace().getLatitude();
        this.longitude = post.getPlace().getLongitude();
        this.date = String.valueOf(post.getDate());
    }

    public PostFormDto(Post post) {
        this.pictureList = pictureList(post.getPictureList());
        this.rating = post.getRating();
        this.review = post.getReview();
        this.category = post.getCategory();
        this.purpose = post.getPurpose();
        this.placeName = post.getPlace().getName();
        this.placeAddress = post.getPlace().getAddress();
        this.latitude = post.getPlace().getLatitude();
        this.longitude = post.getPlace().getLongitude();
        this.date = String.valueOf(post.getDate());
    }

}
