package dku.capstone.foodlog.dto.request;

import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.constant.FoodType;
import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.PostPicture;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class PostFormDto {

    private Long memberId;

    //private List<String> pictureList;

    private Float rating;

    private String review;

    private FoodType type;

    private FoodPurpose purpose;

    //place의 위도, 경도 정보
    //private List<Double> location;

    private String date;
}
