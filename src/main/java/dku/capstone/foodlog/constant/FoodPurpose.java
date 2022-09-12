package dku.capstone.foodlog.constant;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum FoodPurpose {
    FAMILY("가족"),
    FRIEND("친구"),
    COUPLE("데이트"),
    MEETING("회식"),
    SOLO("혼밥"),
    ETC("기타");

    @Getter
    private final String value;

    FoodPurpose(String value) {
        this.value = value;
    }

    private static final Map<String, FoodPurpose> findByValue =
            Stream.of(values()).collect(Collectors.toMap(FoodPurpose::getValue, e -> e));

    public static FoodPurpose valueOfFoodPurpose(String value) {
        return findByValue.get(value);
    }
}
