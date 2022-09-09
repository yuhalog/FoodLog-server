package dku.capstone.foodlog.dto.response;

import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.PostPicture;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostResponse {

    private String member;

    private List<String> pictureList;

    private Integer rating;

    private String review;

    private FoodPurpose purpose;

    private String date;

    private KakaoPlaceResponse.PlaceInfo placeInfo;

    public List<String> pictureList(List<PostPicture> postPictureList) {
        List<String> pictureList = new ArrayList<>();

        for (PostPicture postPicture : postPictureList) {
            pictureList.add(postPicture.getPictureUrl());
        }
        return pictureList;
    }

    public PostResponse(Post post, List<String> pictureList) {
        this.member = post.getMember().getUsername();
        this.pictureList = pictureList;
        this.rating = post.getRating();
        this.review = post.getReview();
        this.purpose = post.getPurpose();
        this.date = post.getDate().toString();
        this.placeInfo = new KakaoPlaceResponse.PlaceInfo(post.getPlace());
    }

    //    private List<Comment> commentList;
}
