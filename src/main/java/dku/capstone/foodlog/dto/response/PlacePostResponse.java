package dku.capstone.foodlog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PlacePostResponse {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private float rating;
    private List<PictureReview> pictureReviews;

    @Builder
    public class PictureReview {
        private String pictureUrl;
        private String review;
    }
}
