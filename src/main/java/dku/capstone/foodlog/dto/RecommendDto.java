package dku.capstone.foodlog.dto;


import dku.capstone.foodlog.constant.FoodPurpose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class RecommendDto {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Request{

        private FoodPurpose foodPurpose;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Response{

        private List<PostDto.Summary> posts;


    }
}
