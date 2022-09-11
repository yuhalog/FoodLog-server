package dku.capstone.foodlog.dto.response;

import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.domain.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel("게시물 조회")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostResponse {

    @ApiModelProperty(example = "test1")
    private String member;

    @ApiModelProperty(dataType = "List", example = "[https://foodlogstorage.s3.ap-northeast-2.amazonaws.com/d857688e-0ed8-4200-8aa0-5b770e30e016.jpeg]")
    private List<String> pictureList;

    @ApiModelProperty(example = "3")
    private Integer rating;

    @ApiModelProperty(example = "정말 맛있어요!")
    private String review;

    @ApiModelProperty(example = "가족")
    private FoodPurpose purpose;

    @ApiModelProperty(example = "2022-09-11")
    private String date;

    private PlaceResponse place;

    public PostResponse(Post post, List<String> pictureList) {
        this.member = post.getMember().getUsername();
        this.pictureList = pictureList;
        this.rating = post.getRating();
        this.review = post.getReview();
        this.purpose = post.getPurpose();
        this.date = post.getDate().toString();
        this.place = new PlaceResponse(post.getPlace());
    }

    //    private List<Comment> commentList;
}
