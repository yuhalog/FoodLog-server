package dku.capstone.foodlog.dto.response;

import dku.capstone.foodlog.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceResponse {

    private Long kakaoId;

    private String name;

    private String address;

    private String category;

    private Double latitude;

    private Double longitude;

    public PlaceResponse(Place place) {
        this.kakaoId = place.getKakaoPlaceId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.category = place.getCategory().getValue();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
    }
}
