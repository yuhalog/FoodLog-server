package dku.capstone.foodlog.dto.response;

import dku.capstone.foodlog.domain.Place;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("장소 조회")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceResponse {

    @ApiModelProperty(example = "1")
    private Long postId;

    @ApiModelProperty(example = "1110210115")
    private Long kakaoId;

    @ApiModelProperty(example = "빨강파이프")
    private String name;

    @ApiModelProperty(example = "경기 용인시 수지구 죽전로144번길 7-5")
    private String address;

    @ApiModelProperty(example = "분식")
    private String category;

    @ApiModelProperty(example = "37.3235861851341")
    private Double latitude;

    @ApiModelProperty(example = "127.124165839734")
    private Double longitude;

    public PlaceResponse(Place place) {
        this.postId = place.getId();
        this.kakaoId = place.getKakaoPlaceId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.category = place.getCategory().getValue();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
    }
}
