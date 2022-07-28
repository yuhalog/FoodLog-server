package dku.capstone.foodlog.dto.response;


import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.constant.FoodType;
import dku.capstone.foodlog.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class PostResponse {
    private Member member;

    private List<String> pictureList;

    private Integer rating;

    private String review;

    private FoodType type;

    private FoodPurpose purpose;

    //place의 음식점 이름 정보
    private String name;

    //place의 음식점 주소 정보
    private String address;

    private String date;

    public PostResponse(Member member,
                        List<String> pictureList,
                        Integer rating,
                        String review,
                        FoodType type,
                        FoodPurpose purpose,
                        String name,
                        String address,
                        String date) {
        this.member = member;
        this.pictureList = pictureList;
        this.rating = rating;
        this.review = review;
        this.type = type;
        this.purpose = purpose;
        this.name = name;
        this.address = address;
        this.date = date;
    }
}
