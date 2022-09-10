package dku.capstone.foodlog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceRequest {

    private String kakaoId;

    private String name;

    private String address;

    private String category;

    private String latitude;

    private String longitude;
}
