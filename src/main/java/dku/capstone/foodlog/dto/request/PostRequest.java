package dku.capstone.foodlog.dto.request;

import dku.capstone.foodlog.constant.FoodPurpose;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("게시물 등록")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {

    @ApiModelProperty(value="회원의 id", example = "1")
    private Long memberId;

    @ApiModelProperty(value="게사물의 별점", example = "3")
    private Integer rating;

    private String review;

    private FoodPurpose purpose;

    private String date;

    private PlaceRequest place;

}
