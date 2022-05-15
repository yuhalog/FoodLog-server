package dku.capstone.foodlog.dto.request;

import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.constant.FoodType;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.PostPicture;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class PostFormDto {

    private List<PostPicture> pictureList = new ArrayList<>();

    private Float rating;

    private FoodType type;

    private FoodPurpose purpose;

    private Place place;

    private Date date;
}
