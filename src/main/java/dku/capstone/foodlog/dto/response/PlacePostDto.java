package dku.capstone.foodlog.dto.response;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.Post;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlacePostDto {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Float rating;
    private List<PictureReview> pictureReviews;

    public PlacePostDto(Place place, List<PictureReview> pictureReviews) {
        this.id = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
        this.rating = place.getAverageRating();
        this.pictureReviews = pictureReviews;
    }

    @Getter
    public static class PictureReview {
        private Long id;
        private String pictureUrl;
        private String review;

        public PictureReview(Post post) {
            this.id = post.getId();
            this.pictureUrl = post.getPictureList().get(0).getPictureUrl();
            this.review = post.getReview();
        }
    }
}
