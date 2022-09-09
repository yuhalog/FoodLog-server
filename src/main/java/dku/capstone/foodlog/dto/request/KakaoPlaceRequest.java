package dku.capstone.foodlog.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KakaoPlaceRequest {

    private String id;

    private String query;

    private Integer page;

    private Integer size;

    @JsonProperty("x")
    private String longitude;

    @JsonProperty("y")
    private String latitude;

}
