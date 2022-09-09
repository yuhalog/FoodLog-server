package dku.capstone.foodlog.constant;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    ETC("기타");

    @Getter
    private final String value;

    FoodCategory(String value) {
        this.value = value;
    }

    private static final Map<String, FoodCategory> findByValue =
            Stream.of(values()).collect(Collectors.toMap(FoodCategory::getValue, e -> e));

    public static FoodCategory valueOfFoodCategory(String value) {
        return findByValue.get(value);
    }
}
