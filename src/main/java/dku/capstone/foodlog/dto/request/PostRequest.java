package dku.capstone.foodlog.dto.request;

import dku.capstone.foodlog.constant.FoodPurpose;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {

    private Long memberId;

    private Integer rating;

    private String review;

    private FoodPurpose purpose;

    private String date;

    private KakaoPlaceRequest kakaoPlace;

}
