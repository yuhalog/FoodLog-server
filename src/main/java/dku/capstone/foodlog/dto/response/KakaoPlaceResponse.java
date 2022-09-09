package dku.capstone.foodlog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dku.capstone.foodlog.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KakaoPlaceResponse {

    private List<PlaceInfo> documents = new ArrayList<>();

    private Meta meta;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PlaceInfo {

        private String id;

        @JsonProperty("place_name")
        private String name;

        @JsonProperty("road_address_name")
        private String address;

        @JsonProperty("x")
        private String longitude;

        @JsonProperty("y")
        private String latitude;

        @JsonProperty("category_name")
        private String category;

        public PlaceInfo(Place place) {
            this.id = place.getId().toString();
            this.name = place.getName();
            this.address = place.getAddress();
            this.longitude = place.getLongitude().toString();
            this.latitude = place.getLatitude().toString();
            this.category = place.getCategory().getValue();
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class Meta {

        private Boolean isEnd;

        private Integer pageableCount;

        private Integer totalCount;
    }
}
