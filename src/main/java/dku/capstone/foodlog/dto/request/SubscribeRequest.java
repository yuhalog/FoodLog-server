package dku.capstone.foodlog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SubscribeRequest {

    private Long memberId;
    private Long subscribeId;
}
