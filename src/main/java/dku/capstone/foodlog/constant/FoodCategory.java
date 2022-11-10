package dku.capstone.foodlog.constant;

import java.util.HashMap;
import lombok.Getter;

import java.util.Map;

public enum FoodCategory {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    ASIAN("아시아음식"),
    DESSERT("간식"),
    CAFE("카페"),
    SNACK("분식"),
    CHICKEN("치킨"),
    BAR("술집"),
    ETC("기타");

    @Getter
    private final String value;

    private static final Map<String, FoodCategory> subCategory = makeSubCategory();

    FoodCategory(String value) {
        this.value = value;
    }

    public static Map<String, FoodCategory> makeSubCategory() {
        Map<String, FoodCategory> subCategory = new HashMap<>();
        subCategory.put("한식", KOREAN);
        subCategory.put("중식", CHINESE);
        subCategory.put("일식", JAPANESE);
        subCategory.put("아시아음식", ASIAN);
        subCategory.put("양식", WESTERN);
        subCategory.put("샐러드", WESTERN);
        subCategory.put("패스트푸드", WESTERN);
        subCategory.put("간식", DESSERT);
        subCategory.put("카페", CAFE);
        subCategory.put("분식", SNACK);
        subCategory.put("치킨", CHICKEN);
        subCategory.put("술집", BAR);
        return subCategory;
    }

    public static FoodCategory findSubCategory(String value) {
        return subCategory.get(value);
    }
}
